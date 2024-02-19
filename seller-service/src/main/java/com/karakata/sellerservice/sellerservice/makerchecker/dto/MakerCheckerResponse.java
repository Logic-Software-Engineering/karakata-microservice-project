package com.karakata.sellerservice.sellerservice.makerchecker.dto;

import com.karakata.sellerservice.sellerservice.staticdata.EntityType;
import com.karakata.sellerservice.sellerservice.staticdata.RequestStatus;
import com.karakata.sellerservice.sellerservice.staticdata.RequestType;
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
