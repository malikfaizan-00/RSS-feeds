package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;

import java.io.IOException;
import java.util.List;

public interface RSSFeedService {
    RSSFeedDto saveRSSFeed(RSSFeedDto rssFeedDto);

    List<RSSFeedDto> getRSSFeedById(String rssFeedId);

    List<RSSFeed> getAllRSSFeeds();

    RSSFeedDto updateRSSFeed(RSSFeedDto rssFeedDto);

    void deleteRSSFeed(Long rssFeedId);

    String analyseRSSFeed(List<String> urls) throws IOException;
}
