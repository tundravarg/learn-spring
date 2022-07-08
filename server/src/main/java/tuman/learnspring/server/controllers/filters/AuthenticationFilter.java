package tuman.learnspring.server.controllers.filters;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class AuthenticationFilter implements Filter {

    private static final Map<String, String> USERS = new HashMap<>();
    static {
        USERS.put("test", "testpwd");
        USERS.put("admin", "adminpwd");
        USERS.put("user", "userpwd");
    }


    private static final Pattern PARSE_AUTH_FILTER = Pattern.compile("(.+)\s(.+)");
    private static final Pattern PARSE_BASIC_AUTH = Pattern.compile("(.+):(.*)");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest)request).getHeader("authorization");
        try {
            if (authorization == null || !checkAuthorization(authorization)) {
                System.out.println("Can not authorize: Bad authorization");
                responseNotAuthorized(response);
                return;
            }
        } catch (Throwable ex) {
            System.err.println("Can not authorize: ERROR: " + ex.getClass() + " " + ex.getMessage());
            ex.printStackTrace(System.err);
            responseNotAuthorized(response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void responseNotAuthorized(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(401);
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=<realm>");
        httpResponse.getOutputStream().println("Unauthorized");
        httpResponse.getOutputStream().flush();
    }

    private boolean checkAuthorization(String authHeader) {
        Matcher matcher = PARSE_AUTH_FILTER.matcher(authHeader);
        if (!matcher.find()) {
            return false;
        }
        String authType = matcher.group(1).toLowerCase();
        String authStr = matcher.group(2);
        return switch (authType) {
            case "basic" -> chechBasicAuthorization(authStr);
            default -> false;
        };
    }

    private boolean chechBasicAuthorization(String authStr) {
        authStr = new String(Base64.getDecoder().decode(authStr));
        Matcher matcher = PARSE_BASIC_AUTH.matcher(authStr);
        if (!matcher.find()) {
            return false;
        }
        String username = matcher.group(1);
        String password = matcher.group(2);
        return checkUsernameAndPassword(username, password);
    }

    private boolean checkUsernameAndPassword(String username, String password) {
        String user = USERS.get(username);
        return user != null && user.equals(password);
    }

}
