package com.karakata.authserver.address.model;

import jakarta.persistence.*;
import lombok.*;
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
@AllArgsConstructor
@SQLDelete(sql = "UPDATE address SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class Address implements Serializable {
    @Id
    @SequenceGenerator(name = "address_seq",
            sequenceName = "address_seq",allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "address_seq")
    private Long id;
    private String fullAddress;
    private String landmark;
    private String city;
    @Column(name = "states")
    private String state;
    private String country;
    private  boolean deleted=Boolean.FALSE;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;


    @UpdateTimestamp
    @Column(insertable = false)
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
