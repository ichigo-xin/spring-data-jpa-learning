package com.example.springdatajpalearning.entity;

import javax.persistence.*;

@Entity
public class Employee {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

//    @OneToOne
//    @JoinColumn(name = "parking_spot_id")
//    private ParkingSpot parkingSpot;
}