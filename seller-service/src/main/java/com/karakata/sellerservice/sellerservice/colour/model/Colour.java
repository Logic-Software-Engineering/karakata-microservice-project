package com.karakata.sellerservice.sellerservice.colour.model;

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
@SQLDelete(sql = "UPDATE colour SET deleted= true WHERE id=?")
@Where(clause = "deleted = false")
public class Colour implements Serializable {
    @Id
    @SequenceGenerator(name = "colour_seq",
            sequenceName = "colour_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "colour_seq")
    private Long id;
    private boolean deleted = Boolean.FALSE;
    private String colourName;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedAt;
}
