package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DataAnalysisService {
    List<RSSFeedDto> getFeeds(String url, String uuid);

    List<List<String>> dataAnalysis(List<RSSFeedDto> rssFeeds) throws IOException;

    Set<String> intersection(List<List<String>> lists);
}
