package com.karakata.userservice.appuserservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestType {
    CREATE("Create"),
    UPDATE("Update"),
    DELETE("Delete");
    private final String requestType;

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "RequestType{" +
                "requestType='" + requestType + '\'' +
                '}';
    }
}
