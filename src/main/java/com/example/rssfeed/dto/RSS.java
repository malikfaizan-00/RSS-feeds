package com.example.rssfeed.dto;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class RSS {
//    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "channel")
    private Channel channel = null;
}
