package com.karakata.sellerservice.sellerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestStatus {
    APPROVED("Approved"),
    PENDING("Pending"),
    DECLINED("Declined");

    private final String requestStatus;

    public String getRequestStatus() {
        return requestStatus;
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
                "requestStatus='" + requestStatus + '\'' +
                '}';
    }
}
