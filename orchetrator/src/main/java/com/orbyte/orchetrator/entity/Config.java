package com.orbyte.orchetrator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
public class Config {
    @Id
    private String config_id;
    private String owner;
    private String name;
    private String value;
    private LocalDateTime updated_on;
    private String modified_by;
}
