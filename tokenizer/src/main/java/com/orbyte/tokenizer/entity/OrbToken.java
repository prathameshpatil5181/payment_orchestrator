package com.orbyte.tokenizer.entity;

import com.orbyte.constants.ModifiedBy;
import com.orbyte.constants.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class OrbToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TId;

    @Column(nullable = false)
    private String tokenId;

    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private Boolean used;

    @Column(nullable = false)
    private BigInteger created;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModifiedBy modifiedBy;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String cardPayload;

}
