package com.example.rssfeed.controller;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import com.example.rssfeed.service.RSSFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class RSSFeedControllerTest {

    @MockBean
    private RSSFeedService rssFeedService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void analyseRSSFeed_ReturnedUUID() throws Exception {
        //given
        given(rssFeedService.analyseRSSFeed(anyList())).willReturn(
                "b050c75f-58c0-4396-ab1d-d5c783cb38a2"
        );
        String urls = "[\"https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss\", \"https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss\"]";

        //when then
        mockMvc.perform(post("/analyse/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(urls))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("b050c75f-58c0-4396-ab1d-d5c783cb38a2")));
    }

    @Test
    void getRSSFeed_ForSavedUUID() throws Exception {
        //
        RSSFeedDto rssFeedDto1 = new RSSFeedDto(1L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeedDto rssFeedDto2 = new RSSFeedDto(2L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeedDto rssFeedDto3 = new RSSFeedDto(3L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        List<RSSFeedDto> rssFeedList = new ArrayList<>();
        rssFeedList.add(rssFeedDto1);
        rssFeedList.add(rssFeedDto2);
        rssFeedList.add(rssFeedDto3);
        given(rssFeedService.getRSSFeedById(anyString())).willReturn(
                rssFeedList
        );

        //when then
        mockMvc.perform(get("/frequency/b050c75f-58c0-4396-ab1d-d5c783cb38a2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getAllRSSFeed_ReturnedListof3() throws Exception {
        //
        RSSFeed rssFeed1 = new RSSFeed(1L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Russia-Ukraine War News: Live Updates - The New York Times", "https://news.google.com/rss/articles/CBMiQWh0dHBzOi8vd3d3Lm55dGltZXMuY29tL2xpdmUvMjAyMy8wMi8yMy93b3JsZC9ydXNzaWEtdWtyYWluZS1uZXdz0gFKaHR0cHM6Ly93d3cubnl0aW1lcy5jb20vbGl2ZS8yMDIzLzAyLzIzL3dvcmxkL3J1c3NpYS11a3JhaW5lLW5ld3MuYW1wLmh0bWw?oc=5", 10);
        RSSFeed rssFeed2 = new RSSFeed(2L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "As chatbots boom, Nvidia sales outlook beats Wall Street expectations - Reuters", "https://news.google.com/rss/articles/CBMiaGh0dHBzOi8vd3d3LnJldXRlcnMuY29tL3RlY2hub2xvZ3kvbnZpZGlhLWZvcmVjYXN0cy1maXJzdC1xdWFydGVyLXJldmVudWUtYWJvdmUtZXhwZWN0YXRpb25zLTIwMjMtMDItMjIv0gEA?oc=5", 20);
        RSSFeed rssFeed3 = new RSSFeed(3L, "b050c75f-58c0-4396-ab1d-d5c783cb38a2", "Lucid shares drop as EV maker misses 2023 delivery expectations - TechCrunch", "https://news.google.com/rss/articles/CBMiYmh0dHBzOi8vdGVjaGNydW5jaC5jb20vMjAyMy8wMi8yMi9sdWNpZC1zaGFyZXMtZHJvcC1hcy1ldi1tYWtlci1taXNzZXMtMjAyMy1kZWxpdmVyeS1leHBlY3RhdGlvbnMv0gFmaHR0cHM6Ly90ZWNoY3J1bmNoLmNvbS8yMDIzLzAyLzIyL2x1Y2lkLXNoYXJlcy1kcm9wLWFzLWV2LW1ha2VyLW1pc3Nlcy0yMDIzLWRlbGl2ZXJ5LWV4cGVjdGF0aW9ucy9hbXAv?oc=5", 30);
        List<RSSFeed> rssFeedList = new ArrayList<>();
        rssFeedList.add(rssFeed1);
        rssFeedList.add(rssFeed2);
        rssFeedList.add(rssFeed3);
        given(rssFeedService.getAllRSSFeeds()).willReturn(
                rssFeedList
        );

        //when then
        mockMvc.perform(get("/frequencies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}