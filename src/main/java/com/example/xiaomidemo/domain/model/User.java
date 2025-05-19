package com.example.xiaomidemo.domain.model;

import javax.management.relation.Role;

public class User {
    private int id;
    private String name;
    private String password;
    private Role role;
    private String email;

    enum Role {
        OWNER,
        ENGINEER,
        EXPERT
    }
}
