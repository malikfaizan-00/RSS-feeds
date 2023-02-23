package com.example.rssfeed.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Item {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "link")
    private String link;
}
