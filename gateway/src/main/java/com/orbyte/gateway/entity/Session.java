package com.orbyte.gateway.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Session {

    @Id
    @Column(nullable = false, unique = true)
    private String sessionId;

    @Column(nullable = false)
    private BigInteger amount;

    @Column(nullable = false)
    private String currency;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @PrePersist
    protected void onCreate() {
        if (this.createdOn == null) {
            this.createdOn = LocalDateTime.now();
        }
    }

}
