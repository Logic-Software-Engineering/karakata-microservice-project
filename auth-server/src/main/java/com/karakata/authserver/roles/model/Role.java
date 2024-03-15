package com.karakata.authserver.roles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.karakata.authserver.appuser.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE roles SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class Role implements Serializable {
    @Id
    @SequenceGenerator(name="role_seq",sequenceName = "role_seq"
            , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "role_gen")
    private Long id;

    @NotNull(message = "Role name must not be null")
    private String roleName;

    private boolean deleted=Boolean.FALSE;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    @Column(insertable = false)
    private Date updatedAt;
}
