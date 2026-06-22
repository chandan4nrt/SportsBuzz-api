package com.chandan.sports.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sport")
@Data
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer players;
    private Boolean isBallNeeded;
}
