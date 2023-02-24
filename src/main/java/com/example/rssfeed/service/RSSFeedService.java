package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;

import java.io.IOException;
import java.util.List;

public interface RSSFeedService {
    RSSFeedDto saveRSSFeed(final RSSFeedDto rssFeedDto);

    List<RSSFeedDto> getRSSFeedById(final String rssFeedId);

    List<RSSFeed> getAllRSSFeeds();

    RSSFeedDto updateRSSFeed(final RSSFeedDto rssFeedDto);

    void deleteRSSFeed(final Long rssFeedId);

    String analyseRSSFeed(final List<String> urls) throws IOException;
}
