package com.dev.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;



@Entity
@Table(name="allocations")
@Data

public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id" , nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name ="asset_id" , nullable=false)
    private Asset asset;

    private LocalDate allocationDate;
    private LocalDate returnDate;


}
