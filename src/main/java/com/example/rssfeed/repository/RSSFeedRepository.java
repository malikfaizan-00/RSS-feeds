package com.example.rssfeed.repository;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RSSFeedRepository extends JpaRepository<RSSFeed, Long> {
    List<RSSFeedDto> findTop3ByUuidOrderByFreqCountDesc(String id);
    RSSFeed findRSSFeedsByUuid(String uuid);
}
