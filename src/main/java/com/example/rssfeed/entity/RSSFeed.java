package com.example.rssfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RSSFeeds")
public class RSSFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    @Column(length = 1000)
    private String title;
    @Column(length = 1000)
    private String link;
    private int freqCount;
}
