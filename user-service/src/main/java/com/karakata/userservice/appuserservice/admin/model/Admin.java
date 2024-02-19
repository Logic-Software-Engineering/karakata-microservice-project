package com.karakata.userservice.appuserservice.admin.model;


import com.karakata.userservice.appuserservice.appuser.model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
@SQLDelete(sql = "UPDATE admin SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class Admin implements Serializable {
    @Id
    @SequenceGenerator(name = "admin_seq",
            sequenceName = "admin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "admin_gen")
    private Long id;

    @Pattern(regexp="[A-Za-z\\s]+", message="First Name should contains alphabets only")
    private String firstName;

    @Pattern(regexp="[A-Za-z\\s]+", message="Last Name should contains alphabets only")
    private String lastName;
    private boolean deleted=Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    @Column(insertable = false)
    private Date updatedAt;
}
