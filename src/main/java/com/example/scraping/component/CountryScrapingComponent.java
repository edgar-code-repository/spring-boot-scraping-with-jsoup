package com.example.scraping.component;

import com.example.scraping.dto.CountryDTO;
import com.example.scraping.repository.file.CountryFileRepositoryImpl;
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
    public void run(String... args) {
        log.info("Running CountryScrapingComponent on startup...");

        log.info("Retrieving list of countries from United Nations webpage...");
        List<CountryDTO> countriesWithScraping = countryService.getCountriesWithScraping();

        log.info("Details of each country are retrieved from Wikipedia...");
        countriesWithScraping.forEach(country -> countryService.getCountryInfoFromWikipedia(country));

        log.info("Details of each country are retrieved from World Bank...");
        countriesWithScraping.forEach(country -> countryService.getCountryInfoFromWorldBank(country));

        log.info("List of countries is stored in H2 database and a file...");
        countryService.saveCountryListToDB(countriesWithScraping);
        //countryService.saveCountryListToFile(countriesWithScraping);

    }
}
