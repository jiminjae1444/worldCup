package com.itbank.worldcup.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Imagefile")
public class FileDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="originalFileName" ,  nullable = false )
    private String originalFileName;

    @Column(name = "storedFileName" , nullable = false)
    private String storedFileName;

    @Column(name = "fileType", nullable = false, length = 50)
    private String fileType;

    @Column(name = "fileSize", nullable = false)
    private long fileSize;

    @Column(name = "wfDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date wfDate = new java.util.Date();  //기본값을 현재 시간으로

    @Column(name = "ufDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ufDate;

}
