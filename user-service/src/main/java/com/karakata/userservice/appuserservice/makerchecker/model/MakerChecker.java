package com.karakata.userservice.appuserservice.makerchecker.model;


import com.karakata.userservice.appuserservice.staticdata.EntityType;
import com.karakata.userservice.appuserservice.staticdata.RequestStatus;
import com.karakata.userservice.appuserservice.staticdata.RequestType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MakerChecker implements Serializable {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    private Long entityId;
    private String oldState;
    private String newState;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column(name = "checker_id")
    private Long adminId;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    @Column(insertable = false)
    private Date updatedAt;
}
