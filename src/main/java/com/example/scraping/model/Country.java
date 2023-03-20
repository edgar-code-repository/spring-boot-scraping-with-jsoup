package com.example.scraping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_country")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int countryId;
    private String name;
    private String officialName;
    private String capital;
    private String urlWikipedia;
    private String urlWorldBank;
    private String region;
    private String incomeCategory;


}
