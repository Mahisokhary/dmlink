package ir.qgdev.qoqnus.dmlink.domain.test.url;

import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpUrlTest {
    private void testHttpUrlFromString(String s) {
        assertEquals(s, HttpUrl.fromString(s).toString());
    }

    @Test
    public void testHttpUrlFromString() {
        testHttpUrlFromString("https://example.com");
        testHttpUrlFromString("http://example.com");
        testHttpUrlFromString("https://example.com:443");
        testHttpUrlFromString("https://example.com:443/api/example");
        testHttpUrlFromString("https://example.com/api/example");
        assertEquals("https://example.com", HttpUrl.fromString("example.com").toString());
        assertThrows(IllegalArgumentException.class, () -> HttpUrl.fromString("https://example./api/example"));
        assertThrows(IllegalArgumentException.class, () -> HttpUrl.fromString("https://example/api/example"));
    }

}
