package com.example.rssfeed.mapper;

import com.example.rssfeed.dto.RSSFeedDto;
import com.example.rssfeed.entity.RSSFeed;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RSSFeedMapper {

    RSSFeedMapper MAPPER = Mappers.getMapper(RSSFeedMapper.class);

    RSSFeedDto mapToRSSFeedDto(RSSFeed user);

    RSSFeed mapToRSSFeed(RSSFeedDto userDto);
}
