package com.example.rssfeed.service;

import com.example.rssfeed.dto.Channel;
import com.example.rssfeed.dto.Item;
import com.example.rssfeed.dto.RSS;
import com.example.rssfeed.dto.RSSFeedDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.rssfeed.util.StopWord.stopwords;
import static java.util.stream.Collectors.toList;

@Service
public class DataAnalysisServiceImp implements DataAnalysisService {
    public List<RSSFeedDto> getFeeds(String url, String uuid) {
        List<Item> rssFeeds = new ArrayList<>();
        try {
            URL urlSource = new URL(url);
            Channel channel = convertXMLToObject(urlSource);
            rssFeeds = channel.getItemList();
        } catch (Exception e) {

        }
        return rssFeeds.parallelStream()
                .map(r -> new RSSFeedDto(null, uuid, r.getTitle(), r.getLink(), 1))
                .collect(toList());
    }

    @Override
    public List<List<String>> dataAnalysis(List<RSSFeedDto> rssFeeds) throws IOException {
        List<List<String>> getAllFeedWords = new ArrayList<>();
        if (stopwords == null) {
            stopwords = Files.readAllLines(Paths.get("src/main/resources/english_stopwords.txt"));
        }

        for (RSSFeedDto rssFeedDto : rssFeeds) {
            ArrayList<String> allWords = Stream.of(rssFeedDto.getTitle().split(" "))
                    .collect(Collectors.toCollection(ArrayList<String>::new));
            allWords.removeAll(stopwords);
            getAllFeedWords.add(allWords);
        }
        return getAllFeedWords;
    }

    @Override
    public Set<String> intersection(List<List<String>> lists) {
        Set<String> intersectionList = new HashSet<>();

        for (int i = 0; i < lists.size(); ++i) {
            for (int j = i + 1; j < lists.size() - 1; ++j) {
                if (lists.get(i).size() > lists.get(j).size()) {
                    for (String str : lists.get(j)) {
                        if (lists.get(i).contains(str)) {
                            if (str.length() > 3) {
                                intersectionList.add(str);
                            }
                        }
                    }
                } else {
                    for (String str : lists.get(i)) {
                        if (lists.get(j).contains(str)) {
                            if (str.length() > 3) {
                                intersectionList.add(str);
                            }
                        }
                    }
                }
            }
        }
        return intersectionList;
    }

    private static Channel convertXMLToObject(URL urlSource) {
        RSS rss = null;
        try {
            JAXBContext context = JAXBContext.newInstance(RSS.class);
            HttpURLConnection http = (HttpURLConnection) urlSource.openConnection();
            http.addRequestProperty("Content-Type", MediaType.APPLICATION_XML_VALUE);
            http.setAllowUserInteraction(false);
            http.setDoInput(true);
            http.setDoOutput(false);
            http.setUseCaches(true);
            http.setRequestMethod("GET");
            http.getContent();
            InputStream is = http.getInputStream();
            Unmarshaller un = context.createUnmarshaller();

            rss = (RSS) un.unmarshal(is);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rss.getChannel().get(0);
    }
}