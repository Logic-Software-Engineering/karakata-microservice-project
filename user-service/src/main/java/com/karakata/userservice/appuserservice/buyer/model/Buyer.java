package com.karakata.userservice.appuserservice.buyer.model;


import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.staticdata.Gender;
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
@SQLDelete(sql = "UPDATE buyer SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class Buyer implements Serializable {
    @Id
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_gen")
    private Long id;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "First name should contains alphabets only")
    private String firstName;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "Last name should contains alphabets only")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

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
