package tuman.learnspring.server.exceptions;


import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;


public class CheckPrivilegesException extends RuntimeException {

    public CheckPrivilegesException(String message) {
        super(message);
    }

    public static CheckPrivilegesException ofUnsupportedPrivileges(
            String username, Set<String> unsupportedPrivileges, String method) {
        return new CheckPrivilegesException(String.format(
                "User '%s' does not have required privileges %s to call '%s' method",
                username,
                unsupportedPrivileges.stream().map(p -> "'" + p + "'").collect(Collectors.toSet()),
                method));
    }

    public static CheckPrivilegesException ofUnsupportedPrivileges(
            String username, Set<String> unsupportedPrivileges, String klass, String method) {
        return ofUnsupportedPrivileges(username, unsupportedPrivileges, klass + "#" + method);
    }

    public static CheckPrivilegesException ofUnsupportedPrivileges(
            String username, Set<String> unsupportedPrivileges, Method method) {
        return ofUnsupportedPrivileges(
                username, unsupportedPrivileges,
                method.getDeclaringClass().getName(),
                method.getName());
    }

}
