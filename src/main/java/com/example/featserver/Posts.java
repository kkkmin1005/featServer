package com.example.featserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Posts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String userId;
    public LocalDate Date;

    public String image;
    public String music;

}
