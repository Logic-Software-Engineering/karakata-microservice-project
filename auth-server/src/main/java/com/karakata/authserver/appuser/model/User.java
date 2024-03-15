package com.karakata.authserver.appuser.model;



import com.karakata.authserver.address.model.Address;
import com.karakata.authserver.roles.model.Role;
import com.karakata.authserver.staticdata.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_gen")
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(unique = true)
    @NotNull(message = "username must not be null")
    private String username;

    @Email(message = "Invalid email")
    @Column(unique = true)
    @NotNull(message = "email must not be null")
    private String email;

    @Column(unique = true)
    @NotNull(message = "mobile must not be null")
    private String mobile;

    @NotNull(message = "password must not be null")
    @Column(length = 60)
    private String password;

    @Transient
    private String confirmPassword;

    private boolean deleted=Boolean.FALSE;

    private Boolean isEnabled = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles=new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    @Column(insertable = false)
    private Date updatedAt;
}
