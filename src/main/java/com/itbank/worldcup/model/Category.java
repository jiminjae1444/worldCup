package com.itbank.worldcup.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false , length = 100)
    private String cName;

    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id" , nullable = false)
    private File file;

    @Column(name = "wcDate" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date wcDate = new java.util.Date();  //기본값을 현재 시간으로

    @Column(name = "ucDate" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ucDate;
}
