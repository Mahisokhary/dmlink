package ir.qgdev.qoqnus.dmlink.presentation.test.session;

import ir.qgdev.qoqnus.dmlink.application.session.SessionService;
import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtAuthentication;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionDto;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Set;

import static ir.qgdev.qoqnus.dmlink.presentation.test.session.SessionTestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SessionSelfRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public UserService userService;

    @Autowired
    public SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void testGet() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();
        Collection<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_SESSION_GET_SELF")
        );

        MockHttpServletRequestBuilder requestBuilder = get("/api/session/@me")
                .with(authentication(JwtAuthentication.fromSession(session, authorities)))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        SessionDto sessionDto = objectMapper.readValue(content, SessionDto.class);
        assertEquals("ID differs from passed ID", session.getId(), sessionDto.id());
        assertEquals("User differs from passed user", user.getId(), sessionDto.user());
    }

}
