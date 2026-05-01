package com.orbyte.orchetrator.entity;

import com.orbyte.constants.ModifiedBy;
import jakarta.persistence.*;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String txn_id;

    //link with user table
    @Column(unique = true)
    private String userId;

    // card details link

    @CreationTimestamp
//    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATETIME", nullable = false)
    private LocalDateTime createdOn;

    @CreationTimestamp
//    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATETIME",nullable = false)
    private LocalDateTime ModifiedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModifiedBy modifiedBy;

    @Column(nullable = false)
    private String ModifyingUser;

}
