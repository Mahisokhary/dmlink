package ir.qgdev.qoqnus.dmlink.domain.url;

import lombok.NonNull;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * A HttpUrl
 * @param secure Is the url with secure protocol (https) or not (http)
 * @param host The host (example.com)
 * @param port The port (443)
 * @param path The path (index.html)
 */
public record HttpUrl(boolean secure, @NonNull String host, Integer port, String path) implements Serializable {
    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^(?:http(|s)://)?([^/:]+\\.[^/:]+)(?::(\\d+))?(/.*)?$");

    /**
     * Extract http url from a string
     */
    public static HttpUrl fromString(String url) {
        var m = HTTP_URL_PATTERN.matcher(url);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid HttpUrl: " + url);
        }

        boolean secure = m.group(1) == null || "s".equals(m.group(1));
        String host = m.group(2);
        Integer port = m.group(3) != null ? Integer.valueOf(m.group(3)) : null;
        String path = m.group(4);

        return new HttpUrl(secure, host, port, path);
    }

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

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(toString());
    }

    private record SerializedForm(String value) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Serial
        private Object readResolve() {
            return HttpUrl.fromString(value);
        }

    }

}
