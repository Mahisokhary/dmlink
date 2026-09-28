package ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private static final String DEFAULT_SECRET = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

    private final Key key;
    private final String issuer;
    private final JwtParser parser;

    public JwtServiceImpl(
            @NonNull @Value("${dmlink.jwt.secret:" + DEFAULT_SECRET + "}") String secret,
            @NonNull @Value("${spring.application.name:dmlink}") String issuer
    ) {
        if (Objects.equals(secret, DEFAULT_SECRET)) {
            log.warn("Default secret is used, ignore if you are debugging, set dmlink.token.secret property for production use");
        }

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.issuer = issuer;
        parser = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .build();
    }

    public String create(String id) {
        Instant in = Instant.now();
        return Jwts.builder()
                .setSubject(id)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(in))
                .setExpiration(Date.from(in.plus(30, ChronoUnit.DAYS)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String verifyAndFetchId(String jwt) {
        try {
            return parser.parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid jwt", e);
        }
    }
}
