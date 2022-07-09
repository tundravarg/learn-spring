package tuman.learnspring.server.dtos;


import java.util.Set;


public class UserCtx {

    private String username;
    private Set<String> privileges;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<String> privileges) {
        this.privileges = privileges;
    }

}
