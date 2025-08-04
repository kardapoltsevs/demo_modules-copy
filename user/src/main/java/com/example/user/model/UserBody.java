package com.example.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserBody {
    private String name;
    private String email;
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
