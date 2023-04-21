package com.linetime.backend.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 60)
    private String name;
}