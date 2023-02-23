package com.example.rssfeed.repository;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class RSSFeedRepositoryTest {

    @Autowired
    private RSSFeedRepository rssFeedRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGetRSSFeedByUuid_returnsRSSFeedDetails() {
        //given
        RSSFeed savedRSSFeed = testEntityManager.persistFlushFind(new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10));

        //when
        RSSFeed rssFeed = rssFeedRepository.findRSSFeedsByUuid("b050c75f-58c0-4396-ab1d-d5c783cb38a2");

        //then
        then(rssFeed.getUuid()).isNotNull();
        then(rssFeed.getUuid()).isEqualTo(savedRSSFeed.getUuid());
    }


    @Test
    void testGetRSSFeedByUuid_returnsTop3FreqCount() {
        //given
        RSSFeed savedRSSFeed1 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeed savedRSSFeed2 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeed savedRSSFeed3 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        Arrays.asList(savedRSSFeed1, savedRSSFeed2, savedRSSFeed3).forEach(testEntityManager::persistFlushFind);


        //when
        List<RSSFeedDto> rssFeedDtoList = rssFeedRepository.findTop3ByUuidOrderByFreqCountDesc("b050c75f-58c0-4396-ab1d-d5c783cb38a2");

        //then
        then(rssFeedDtoList.size()).isEqualTo(3);
        then(rssFeedDtoList.get(0).getFreqCount()).isEqualTo(30);
        then(rssFeedDtoList.get(2).getFreqCount()).isEqualTo(10);
    }

}
