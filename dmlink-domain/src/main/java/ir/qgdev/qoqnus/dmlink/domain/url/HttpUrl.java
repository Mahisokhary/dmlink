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
    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^(?:http(|s)://)?([^/:]+\\.[^/:]+)(?::(\\d+))?(/.*)?$");

    public HttpUrl {
        if (host.isEmpty())
            throw new IllegalArgumentException("host is required");
    }

    /**
     * Convert http url to an http url in string format
     */
    @Override
    public @NonNull String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(secure ? "https://" : "http://");
        builder.append(host);
        if (port != null) builder.append(":").append(port);
        if (path != null && !path.isEmpty()) {
            if (!path.startsWith("/")) builder.append("/");
            builder.append(path);
        }
        return builder.toString();
    }

}
