package com.karakata.authserver.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleState {
    DELETED("Deleted"),
    ACTIVE("Active");

    private final String roleState;

    public String getRoleState() {
        return roleState;
    }
}
