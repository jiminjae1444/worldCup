package com.itbank.worldcup.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Column(name = "wiDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wiDate;

    @Column(name = "uiDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uiDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "gender", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
