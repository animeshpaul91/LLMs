package com.webapp.example.webappdemo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "ID", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable=false)
    //private String manufacturer;
    private String firstName;
    @NotNull
    @Column(nullable=false)
    //private String model;
    private String lastName;
    @NotNull
    @Column(nullable=false)
    //private String type;
    private String designation;
}