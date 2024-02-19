package com.karakata.userservice.appuserservice.seller.model;



import com.karakata.userservice.appuserservice.appuser.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE seller SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Seller implements Serializable {
    @Id
    @SequenceGenerator(name = "seller_seq",
            sequenceName = "seller_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seller_gen")
    private Long id;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "seller's name should contains alphabets only")
    @Column(unique = true, nullable = false)
    private String sellerName;
    private String natureOfBusiness;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "Rep's name should contains alphabets only")
    private String companyRepresentative;

    @Column(unique = true)
    private String taxId;
    private boolean deleted=Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
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
