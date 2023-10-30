package com.example.springdatajpalearning.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}