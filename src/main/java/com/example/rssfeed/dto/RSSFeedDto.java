package com.example.rssfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RSSFeedDto {
    private Long id;
    private String uuid;
    private String title;
    private String link;
    private int freqCount;
}
