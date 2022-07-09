package tuman.learnspring.server.controllers.filters;


import org.springframework.stereotype.Component;
import tuman.learnspring.server.dtos.UserCtx;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class AuthenticationFilter implements Filter {

    private static class User {
        public String username;
        public String password;
        public Set<String> privileges;
        public User(String username, String password, String... privileges) {
            this.username = username;
            this.password = password;
            this.privileges = Arrays.stream(privileges).collect(Collectors.toSet());
        }
    }


    private static final Map<String, User> USERS = new HashMap<>();
    static {
        final Consumer<User> putUser = (User user) -> USERS.put(user.username, user);
        putUser.accept(new User("user", "userpwd", "ping"));
        putUser.accept(new User("admin", "adminpwd", "admin"));
    }


    private static final Pattern PARSE_AUTH_FILTER = Pattern.compile("(.+)\s(.+)");
    private static final Pattern PARSE_BASIC_AUTH = Pattern.compile("(.+):(.*)");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest)request).getHeader("authorization");
        try {
            User user = authorization != null ? checkAuthorization(authorization) : null;
            if (user == null) {
                System.out.println("Can not authorize: Bad authorization");
                responseNotAuthorized(response);
                return;
            }
            UserCtx userCtx = getUserCtx(user);
            ((HttpServletRequest) request).getSession().setAttribute("userCtx", userCtx);
        } catch (Throwable ex) {
            System.err.println("Can not authorize: ERROR: " + ex.getClass() + " " + ex.getMessage());
            ex.printStackTrace(System.err);
            responseNotAuthorized(response);
            return;
        }
        chain.doFilter(request, response);
    }

    private UserCtx getUserCtx(User user) {
        UserCtx userCtx = new UserCtx();
        userCtx.setUsername(user.username);
        userCtx.setPrivileges(Collections.unmodifiableSet(user.privileges));
        return userCtx;
    }

    private void responseNotAuthorized(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(401);
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=<realm>");
        httpResponse.getOutputStream().println("Unauthorized");
        httpResponse.getOutputStream().flush();
    }

    private User checkAuthorization(String authHeader) {
        Matcher matcher = PARSE_AUTH_FILTER.matcher(authHeader);
        if (!matcher.find()) {
            return null;
        }
        String authType = matcher.group(1).toLowerCase();
        String authStr = matcher.group(2);
        return switch (authType) {
            case "basic" -> checkBasicAuthorization(authStr);
            default -> null;
        };
    }

    private User checkBasicAuthorization(String authStr) {
        authStr = new String(Base64.getDecoder().decode(authStr));
        Matcher matcher = PARSE_BASIC_AUTH.matcher(authStr);
        if (!matcher.find()) {
            return null;
        }
        String username = matcher.group(1);
        String password = matcher.group(2);
        return checkUsernameAndPassword(username, password);
    }

    private User checkUsernameAndPassword(String username, String password) {
        User user = USERS.get(username);
        return user != null && user.password.equals(password) ? user : null;
    }

}
