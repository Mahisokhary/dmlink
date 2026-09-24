package ir.qgdev.qoqnus.dmlink.presentation.security.authorize;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Matches a request by its query params
 */
public class QueryParamRequestMatcher implements RequestMatcher {
    private final RequestMatcher pathRequestMatcher;
    private final String paramName;
    private final String expectedValue;

    /**
     * @param method Expected method of request
     * @param pattern Expected pattern of request path
     * @param paramName Query param that is expected to exist
     */
    public QueryParamRequestMatcher(@NonNull HttpMethod method, @NonNull String pattern, @NonNull String paramName) {
        this(method, pattern, paramName, null);
    }

    /**
     * @param method Expected method of request
     * @param pattern Expected pattern of request path
     * @param paramName Query param that is expected to exist
     * @param expectedValue Expected value of the param (ignored if null)
     */
    public QueryParamRequestMatcher(@NonNull HttpMethod method, @NonNull String pattern, @NonNull String paramName, @Nullable String expectedValue) {
        this.pathRequestMatcher = PathPatternRequestMatcher.pathPattern(method, pattern);
        this.paramName = paramName;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean matches(@NonNull HttpServletRequest request) {
        if (pathRequestMatcher.matches(request)) {
            String actualValue = request.getParameter(paramName);
            if (actualValue != null) {
                return expectedValue == null || expectedValue.equals(actualValue);
            }
        }
        return false;
    }
}