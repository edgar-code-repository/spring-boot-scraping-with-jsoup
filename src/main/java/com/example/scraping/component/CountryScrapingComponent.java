package com.example.scraping.component;

import com.example.scraping.dto.CountryDTO;
import com.example.scraping.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CountryScrapingComponent implements CommandLineRunner {

    @Autowired
    private CountryService countryService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Running CountryScrapingComponent on startup...");

        List<CountryDTO> countriesWithScraping = countryService.getListCountriesWithScraping();


    }
}
