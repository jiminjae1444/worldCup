package com.itbank.worldcup.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "match_data")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;  // 사용자 (외래키)

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;  // 세션 (외래키)

    @ManyToOne
    @JoinColumn(name = "item1_id", nullable = false)
    private Items item1;  // 첫 번째 아이템 (외래키)

    @ManyToOne
    @JoinColumn(name = "item2_id", nullable = false)
    private Items item2;  // 두 번째 아이템 (외래키)

    @ManyToOne
    @JoinColumn(name = "choose_item_id", nullable = false)
    private Items chooseItem;  // 선택된 아이템 (외래키)

    @Column(name = "wmDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wmDate = new Date();  // 매치 일자
}
