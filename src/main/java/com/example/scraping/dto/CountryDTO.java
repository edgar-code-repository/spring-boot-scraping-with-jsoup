package com.example.scraping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class CountryDTO {

    private int id;
    private String name;
    private String url;

}
