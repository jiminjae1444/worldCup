package com.itbank.worldcup.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;

@Data
@Entity
@Table(name = "session_data")
public class SessionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "session_id", length = 255)
    private String sessionId;  // 세션 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDTO user;  // 사용자 (외래키)

    @Column(name = "session_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionDate = new Date();  // 세션 시작 일자

    @Column(name = "last_activity", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivity = new Date();  // 마지막 활동 일자

}
