package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface DataAnalysisService {
    List<RSSFeedDto> getFeeds(final String url, final String uuid);

    List<List<String>> dataAnalysis(final List<RSSFeedDto> rssFeeds) throws IOException;

//    Set<String> intersection(List<List<String>> lists);

    <T, C extends Collection<T>> C intersection(C newCollection, List<List<T>> collections);
}
