package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
class DataAnalysisServiceTest {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @Test
    void getFeeds_returnsSizeGreaterThan1() {
        //given
        String url = "https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss";
        String uuid = UUID.randomUUID().toString();

        //when
        List<RSSFeedDto> listOfRssFeeds = dataAnalysisService.getFeeds(url, uuid);

        //then
        then(listOfRssFeeds.size()).isNotZero();
        then(listOfRssFeeds.size()).isGreaterThan(1);
        then(listOfRssFeeds.get(0).getTitle()).isNotEmpty();
        then(listOfRssFeeds.get(0).getLink()).isNotEmpty();
    }

    @Test
    void dataAnalysis() throws IOException {
        //given
        RSSFeedDto rssFeedDto1 = new RSSFeedDto(1L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeedDto rssFeedDto2 = new RSSFeedDto(2L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeedDto rssFeedDto3 = new RSSFeedDto(3L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        List<RSSFeedDto> rssFeeds = new ArrayList<>();
        rssFeeds.add(rssFeedDto1);
        rssFeeds.add(rssFeedDto2);
        rssFeeds.add(rssFeedDto3);

        //when
        List<List<String>> rssFeedDtoList = dataAnalysisService.dataAnalysis(rssFeeds);

        //then
        then(rssFeedDtoList.size()).isGreaterThan(1);
        then(rssFeedDtoList.size()).isEqualTo(3);
    }

    @Test
    void intersection_returns3IntegerValue() {
        //given
        List<Integer> l1 = List.of(1, 3, 5, 7, 9, 11, 13);
        List<Integer> l2 = List.of(1, 2, 3, 5, 8, 13);
        List<Integer> l3 = List.of(2, 3, 5, 7, 11, 13);

        //when
        Set<Integer> rssFeedIntersection = dataAnalysisService.intersection(new HashSet<>(), l1, l2, l3);
        Set<Integer> intersection = new HashSet(Arrays.asList(3, 5, 13));

        //then
        then(intersection).isEqualTo(rssFeedIntersection);
    }

    @Test
    void intersection_returns2StringValue() {
        //given
        List<String> l1 = Arrays.asList("red", "blue", "blue", "green", "red");
        List<String> l2 = Arrays.asList("red", "green", "green", "yellow");

        //when
        Set<String> actualValue = dataAnalysisService.intersection(new HashSet<>(), l1, l2);
        Set<String> expectedValue = new HashSet(Arrays.asList("red", "green"));

        //then
        then(actualValue).isEqualTo(expectedValue);
    }
}