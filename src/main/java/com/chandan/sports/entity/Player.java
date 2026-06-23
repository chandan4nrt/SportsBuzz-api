package com.chandan.sports.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false) // Creates 'sport_id' column in the player table
    private Sport sport;

    private String speciality;

    private String country;
}
