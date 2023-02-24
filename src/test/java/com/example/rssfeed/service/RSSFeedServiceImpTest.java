package com.example.rssfeed.service;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import com.example.rssfeed.repository.RSSFeedRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
class RSSFeedServiceTest {

    @Autowired
    private RSSFeedRepository rssFeedRepository;
    @Autowired
    private RSSFeedService rssFeedService;

    @Test
    @DisplayName("Returning saved RSSFeed from service layer")
    void saveRSSFeed_returnsRSSFeedFromServiceLayer() {
        //given
        RSSFeed savedRSSFeed = rssFeedRepository.save(new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10));

        //when
        List<RSSFeedDto> retrievedRSSFeed = rssFeedService.getRSSFeedById("b050c75f-58c0-4396-ab1d-d5c783cb38a2");

        //then
        then(retrievedRSSFeed.size()).isEqualTo(1);
        then(retrievedRSSFeed.get(0).getUuid()).isEqualTo(savedRSSFeed.getUuid());
    }

    @Test
    void getRSSFeedById_returnsRSSFeedFromServiceLayer() {
        //given
        RSSFeed savedRSSFeed1 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeed savedRSSFeed2 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeed savedRSSFeed3 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        rssFeedRepository.save(savedRSSFeed1);
        rssFeedRepository.save(savedRSSFeed2);
        rssFeedRepository.save(savedRSSFeed3);

        //when
        List<RSSFeedDto> rssFeedDtoList = rssFeedService.getRSSFeedById("b050c75f-58c0-4396-ab1d-d5c783cb38a2");

        //then
        then(rssFeedDtoList.size()).isEqualTo(3);
        then(rssFeedDtoList.get(0).getFreqCount()).isEqualTo(savedRSSFeed3.getFreqCount());
        then(rssFeedDtoList.get(2).getFreqCount()).isEqualTo(savedRSSFeed1.getFreqCount());
        then(rssFeedDtoList.get(1).getFreqCount()).isEqualTo(savedRSSFeed2.getFreqCount());
    }

    @Test
    void getAllRSSFeeds_returnsAllRSSFeedFromServiceLayer() {
        //given
        RSSFeed savedRSSFeed1 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeed savedRSSFeed2 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeed savedRSSFeed3 = new RSSFeed(null, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        rssFeedRepository.save(savedRSSFeed1);
        rssFeedRepository.save(savedRSSFeed2);
        rssFeedRepository.save(savedRSSFeed3);

        //when
        List<RSSFeed> rssFeedDtoList = rssFeedService.getAllRSSFeeds();

        //then
        then(rssFeedDtoList.size()).isEqualTo(3);
    }

    @Test
    void updateRSSFeed() {
    }

    @Test
    void deleteRSSFeed() {
    }

    @Test
    void analyseRSSFeed_returnsUUIDFromServiceLayer() throws IOException {

        //given
        List<String> urls = new ArrayList<>();
        urls.add("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");

        //when
        String serviceuuid = rssFeedService.analyseRSSFeed(urls);

        //then
        then(serviceuuid).isNotNull();
        then(serviceuuid).isNotEmpty();
    }
}