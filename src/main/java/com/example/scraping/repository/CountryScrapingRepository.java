package com.example.scraping.repository;

import com.example.scraping.dto.CountryDTO;

import java.util.List;

public interface CountryScrapingRepository {

    List<CountryDTO> getCountries();


}
