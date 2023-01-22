package com.example.scraping.service;

import com.example.scraping.dto.CountryDTO;
import com.example.scraping.repository.CountryScrapingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CountryService {

    @Autowired
    private CountryScrapingRepository countryScrapingRepository;

    public List<CountryDTO> getListCountriesWithScraping() {
        return countryScrapingRepository.getCountries();
    }


}
