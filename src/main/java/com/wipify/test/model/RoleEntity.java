package com.wipify.test.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.AUTO;

public class RoleEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true , nullable = false)
    private String name;
}
