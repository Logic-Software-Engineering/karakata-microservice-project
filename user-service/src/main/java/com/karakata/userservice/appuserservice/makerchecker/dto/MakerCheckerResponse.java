package com.karakata.userservice.appuserservice.makerchecker.dto;

import com.karakata.userservice.appuserservice.staticdata.EntityType;
import com.karakata.userservice.appuserservice.staticdata.RequestStatus;
import com.karakata.userservice.appuserservice.staticdata.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakerCheckerResponse {
    private String id;
    private EntityType entityType;
    private Long entityId;
    private RequestType requestType;
    private String oldState;
    private String newState;
    private RequestStatus requestStatus;
    private Long adminId;
    private Date createdAt;
    private Date updatedAt;
}
