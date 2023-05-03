package com.example.motorcycleshop.model;

public class RoleToUser {

    private String userRoleName;

    private String roleName;

    public RoleToUser(String userRoleName, String roleName) {
        this.userRoleName = userRoleName;
        this.roleName = roleName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
