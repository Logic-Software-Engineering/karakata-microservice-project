package com.karakata.userservice.appuserservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String gender;

    public String getGender() {
        return gender;
    }
}
