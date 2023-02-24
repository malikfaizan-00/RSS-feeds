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
    public RSSFeedDto saveRSSFeed(final RSSFeedDto rssFeedDto) {
        RSSFeed rssFeed = RSSFeedMapper.MAPPER.mapToRSSFeed(rssFeedDto);

        RSSFeed savedRSSFeed = rssFeedRepository.save(rssFeed);

        RSSFeedDto savedUserDto = RSSFeedMapper.MAPPER.mapToRSSFeedDto(savedRSSFeed);
        return savedUserDto;
    }

    @Override
    public List<RSSFeedDto> getRSSFeedById(final String rssFeedId) {
        return rssFeedRepository.findTop3ByUuidOrderByFreqCountDesc(rssFeedId);
    }

    @Override
    public List<RSSFeed> getAllRSSFeeds() {
        return rssFeedRepository.findAll();
    }

    @Override
    public RSSFeedDto updateRSSFeed(final RSSFeedDto rssFeedDto) {
        return null;
    }

    @Override
    public void deleteRSSFeed(final Long rssFeedId) {

    }

    @Override
    public String analyseRSSFeed(final List<String> urls) throws IOException {
        String uuid = UUID.randomUUID().toString();
        List<RSSFeedDto> listOfRssFeeds = new ArrayList<>();
        for (String url : urls) {
            if (validator.isValid(url)) {
                listOfRssFeeds.addAll(dataAnalysisService.getFeeds(url, uuid));
            }
        }
        List<List<String>> analizedRSSFeed = dataAnalysisService.dataAnalysis(listOfRssFeeds);
        if (!analizedRSSFeed.isEmpty()) {
            Set<String> intersection = new HashSet<>();
            for (int i = 0; i < analizedRSSFeed.size(); ++i) {
                for (int j = i + 1; j < analizedRSSFeed.size(); ++j) {
                    intersection.addAll(dataAnalysisService.intersection(new HashSet<>(), analizedRSSFeed.get(i), analizedRSSFeed.get(j)));
                }
            }

            for (RSSFeedDto rssFeedDto : listOfRssFeeds) {
                ArrayList<String> allWords = Stream.of(rssFeedDto.getTitle().split(" "))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
                allWords.removeAll(stopwords);

                int freqCount = 0;
                for (String key : allWords) {
                    freqCount += Collections.frequency(intersection, key);
                }
                rssFeedDto.setFreqCount(freqCount);
                saveRSSFeed(rssFeedDto);
            }
        }
        return uuid;
    }
}
