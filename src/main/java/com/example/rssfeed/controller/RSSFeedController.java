package com.example.rssfeed.controller;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import com.example.rssfeed.service.RSSFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RSSFeedController {

    @Autowired
    private RSSFeedService rssFeedService;

    @RequestMapping(
            value = "/analyse/new",
            method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> analyseRSSFeed(
            @RequestBody final List<String> urls
    ) throws IOException {
        if (urls.size() <= 1) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        String uuid = rssFeedService.analyseRSSFeed(urls);
        return new ResponseEntity<>(uuid, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/frequency/{id}",
            method = GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RSSFeedDto>> getRSSFeed(
            @PathVariable("id") String id
    ) {
        //TODO: how to send response if nothing was found? Which HTTP code?
        List<RSSFeedDto> rssFeedList = rssFeedService.getRSSFeedById(id);
        if (rssFeedList.size() > 0)
            return ResponseEntity.ok().body(rssFeedList);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(
            value = "/frequencies",
            method = GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RSSFeed>> getAllRSSFeed() {
        //TODO: how to send response if nothing was found? Which HTTP code?
        return ResponseEntity.ok().body(rssFeedService.getAllRSSFeeds());
    }
}
