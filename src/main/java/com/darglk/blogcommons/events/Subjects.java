package com.darglk.blogcommons.events;

import lombok.Getter;

@Getter
public enum Subjects {
    UserCreated("user_created");

    Subjects(String subject) { this.subject = subject; }

    private final String subject;
}
