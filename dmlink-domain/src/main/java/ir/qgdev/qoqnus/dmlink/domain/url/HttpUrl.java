package ir.qgdev.qoqnus.dmlink.domain.url;

import lombok.NonNull;

import java.util.regex.Pattern;

/**
 * A HttpUrl
 * @param secure Is the url with secure protocol (https) or not (http)
 * @param host The host (example.com)
 * @param port The port (443)
 * @param path The path (index.html)
 */
public record HttpUrl(boolean secure, @NonNull String host, Integer port, String path) {
    public HttpUrl {
        if (host.isEmpty())
            throw new IllegalArgumentException("host is required");
    }

}
