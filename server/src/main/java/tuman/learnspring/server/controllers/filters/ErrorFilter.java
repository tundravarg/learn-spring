package tuman.learnspring.server.controllers.filters;


import org.springframework.stereotype.Component;
import org.springframework.web.util.NestedServletException;
import tuman.learnspring.server.exceptions.CheckPrivilegesException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ErrorFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable error) {
            processError(error, (HttpServletRequest)request, (HttpServletResponse)response);
        }
    }

    private void processError(Throwable error, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (error instanceof NestedServletException) {
            error = error.getCause();
        }

        System.err.printf("Could not process request '%s': %s\n",
                request.getServletPath(),
                error.getMessage());

        int code;
        String message;
        if (error instanceof CheckPrivilegesException) {
            code = 403;
            message = "Forbidden";
        } else {
            code = 500;
            message = "Unknown server error";
        }
        response.setStatus(code);
        response.getOutputStream().println(message);
        response.getOutputStream().flush();
    }

}
