package com.karakata.sellerservice.sellerservice.makerchecker.model;

import com.karakata.sellerservice.sellerservice.staticdata.EntityType;
import com.karakata.sellerservice.sellerservice.staticdata.RequestStatus;
import com.karakata.sellerservice.sellerservice.staticdata.RequestType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cart_item SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
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
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedAt;
}
