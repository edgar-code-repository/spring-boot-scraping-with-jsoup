package com.example.scraping.service;

import com.example.scraping.dto.CountryDTO;
import com.example.scraping.model.Country;
import com.example.scraping.repository.db.CountryRepository;
import com.example.scraping.repository.file.CountryFileRepository;
import com.example.scraping.repository.scraping.CountryScrapingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CountryService {

    @Autowired
    private CountryScrapingRepository countryScrapingRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryFileRepository countryFileRepository;

    public List<CountryDTO> getCountriesWithScraping() {
        return countryScrapingRepository.getCountries();
    }

    public CountryDTO getCountryInfoFromWikipedia(CountryDTO countryDTO) {
        return countryScrapingRepository.getCountryDetailsFromWikipedia(countryDTO);
    }

    public CountryDTO getCountryInfoFromWorldBank(CountryDTO countryDTO) {
        return countryScrapingRepository.getCountryDetailsFromWorldBank(countryDTO);
    }

    public void saveCountryListToDB(List<CountryDTO> countries) {
        log.info("[saveCountryListToDB][countries size: " + countries.size() + "]");
        ModelMapper modelMapper = new ModelMapper();

        countries.forEach(countryDTO -> {
            Country country = modelMapper.map(countryDTO, Country.class);
            countryRepository.save(country);
        });

        log.info("[saveCountriesList][countries saved successfully to DB!]");
    }

    public void saveCountryListToFile(List<CountryDTO> countries) {
        log.info("[saveCountryListToFile][countries size: " + countries.size() + "]");
        countryFileRepository.saveCountryList(countries);
    }


}
