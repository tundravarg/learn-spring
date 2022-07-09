package tuman.learnspring.server.controllers.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tuman.learnspring.server.annotations.CheckPrivileges;
import tuman.learnspring.server.dtos.UserCtx;
import tuman.learnspring.server.exceptions.CheckPrivilegesException;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Aspect
@Component
public class CheckPrivilegesAspect {

    @Around("@annotation(tuman.learnspring.server.annotations.CheckPrivileges)")
    public Object checkPrivileges(ProceedingJoinPoint pjp) throws Throwable {
        UserCtx userCtx = getUserContext();
        if (userCtx == null) {
            throw new IllegalStateException("No User Context");
        }

        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        CheckPrivileges checkPrivilegesAnnotation = method.getAnnotation(CheckPrivileges.class);
        String[] methodPrivileges = checkPrivilegesAnnotation.value();

        Set<String> unsupportedPrivileges = Arrays.stream(methodPrivileges)
                .filter(p -> !userCtx.getPrivileges().contains(p))
                .collect(Collectors.toSet());
        if (!unsupportedPrivileges.isEmpty()) {
            throw CheckPrivilegesException.ofUnsupportedPrivileges(userCtx.getUsername(), unsupportedPrivileges, method);
        }

        return pjp.proceed();
    }

    private static UserCtx getUserContext() {
        return Optional.ofNullable(getCurrentSession())
                .map(session -> session.getAttribute("userCtx"))
                .filter(o -> o instanceof UserCtx)
                .map(o -> (UserCtx)o)
                .stream().findAny()
                .orElse(null);
    }

    private static HttpSession getCurrentSession() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest().getSession();
        } else {
            return null;
        }
    }

}
