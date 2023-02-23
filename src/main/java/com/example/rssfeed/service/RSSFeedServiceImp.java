package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import com.example.rssfeed.mapper.RSSFeedMapper;
import com.example.rssfeed.repository.RSSFeedRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.rssfeed.util.StopWord.stopwords;

@Service
public class RSSFeedServiceImp implements RSSFeedService {
    UrlValidator validator = new UrlValidator();
    @Autowired
    private DataAnalysisService dataAnalysisService;
    @Autowired
    private RSSFeedRepository rssFeedRepository;

    @Override
    public RSSFeedDto saveRSSFeed(RSSFeedDto rssFeedDto) {
        RSSFeed rssFeed = RSSFeedMapper.MAPPER.mapToRSSFeed(rssFeedDto);

        RSSFeed savedRSSFeed = rssFeedRepository.save(rssFeed);

        RSSFeedDto savedUserDto = RSSFeedMapper.MAPPER.mapToRSSFeedDto(savedRSSFeed);
        return savedUserDto;
    }

    @Override
    public List<RSSFeedDto> getRSSFeedById(String rssFeedId) {
        return rssFeedRepository.findTop3ByUuidOrderByFreqCountDesc(rssFeedId);
    }

    @Override
    public List<RSSFeed> getAllRSSFeeds() {
        return rssFeedRepository.findAll();
    }

    @Override
    public RSSFeedDto updateRSSFeed(RSSFeedDto rssFeedDto) {
        return null;
    }

    @Override
    public void deleteRSSFeed(Long rssFeedId) {

    }

    @Override
    public String analyseRSSFeed(List<String> urls) throws IOException {
        String uuid = UUID.randomUUID().toString();
        for (String url : urls) {
            if (validator.isValid(url)) {
                List<RSSFeedDto> listOfRssFeeds = dataAnalysisService.getFeeds(url, uuid);

                List<List<String>> analizedRSSFeed = dataAnalysisService.dataAnalysis(listOfRssFeeds);
                if (!analizedRSSFeed.isEmpty()) {
                    Set<String> intersection = dataAnalysisService.intersection(analizedRSSFeed);
                    for (RSSFeedDto rssFeedDto : listOfRssFeeds) {
                        ArrayList<String> allWords = Stream.of(rssFeedDto.getTitle().split(" "))
                                .collect(Collectors.toCollection(ArrayList<String>::new));
                        allWords.removeAll(stopwords);

                        rssFeedDto.setFreqCount(Collections.frequency(allWords, intersection));
                        saveRSSFeed(rssFeedDto);
                    }
                }
            }
        }
        return uuid;
    }
}
