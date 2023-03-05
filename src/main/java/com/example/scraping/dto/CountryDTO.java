package com.example.scraping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    private int countryId;
    private String name;
    private String officialName;
    private String urlWikipedia;
    private String capital;

}
