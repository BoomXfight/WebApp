package com.andrej.springboot.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {

    CONTACT_READ("contact:read"),
    CONTACT_READ1("contact:read1"),
    CONTACT_CREATE("contact:create"),
    CONTACT_UPDATE("contact:update"),
    CONTACT_DELETE("contact:delete");

    private final String permission;
}
