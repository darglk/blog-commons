package com.darglk.blogcommons.model;

import lombok.Getter;

import java.security.Principal;

@Getter
public class UserPrincipal implements Principal {
    private final String id;
    private final String name;
    private final String sessionId;

    public UserPrincipal(String id, String name, String sessionId) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        return name;
    }
}
