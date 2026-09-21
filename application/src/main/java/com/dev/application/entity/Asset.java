package com.dev.application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="assets")
@Data

public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String serialNumber;

    private String category;
    private String status;
    }
