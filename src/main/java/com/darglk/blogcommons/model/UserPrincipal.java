package com.darglk.blogcommons.model;

import lombok.Getter;

import java.security.Principal;

@Getter
public class UserPrincipal implements Principal {
    private final String id;
    private final String name;

    public UserPrincipal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
