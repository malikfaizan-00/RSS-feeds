package com.example.rssfeed.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {
    @XmlElement(name = "item")
    private List<Item> itemList = null;
}
