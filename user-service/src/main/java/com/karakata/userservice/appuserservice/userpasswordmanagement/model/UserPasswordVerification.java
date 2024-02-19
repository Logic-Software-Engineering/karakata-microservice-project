package com.karakata.userservice.appuserservice.userpasswordmanagement.model;


import com.karakata.userservice.appuserservice.appuser.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_password_verification SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class UserPasswordVerification implements Serializable {
    private static final Integer EXPIRATION_TIME=30;
    @Id
    @SequenceGenerator(name = "user_password_verification_seq",
            sequenceName = "user_password_verification_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_password_verification_gen")
    private Long id;
    private String token;
    private Date expectedExpirationTime;

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

    public UserPasswordVerification(String token, User appUser) {
        super();
        this.token = token;
        this.user = appUser;
        this.expectedExpirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }

    public UserPasswordVerification(String token) {
        super();
        this.token = token;
        this.expectedExpirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(Integer expirationTime) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
