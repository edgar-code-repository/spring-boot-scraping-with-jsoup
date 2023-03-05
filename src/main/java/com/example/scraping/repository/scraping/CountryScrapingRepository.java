package com.example.scraping.repository.scraping;

import com.example.scraping.dto.CountryDTO;

import java.util.List;

public interface CountryScrapingRepository {

    List<CountryDTO> getCountries();

    CountryDTO getCountryDetailsFromWikipedia(CountryDTO countryDTO);

}
