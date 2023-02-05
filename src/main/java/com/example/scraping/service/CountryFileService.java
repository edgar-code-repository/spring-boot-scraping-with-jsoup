package com.example.scraping.service;

import com.example.scraping.dto.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CountryFileService {

    @Value("${countriesFilePath}")
    private String countriesFilePath;

    @Value("${countriesFileExtension}")
    private String countriesFileExtension;
    public void saveCountryListToFile(List<CountryDTO> countries) {
        log.info("[saveCountryListToFile][Saving countries to file...]");
        try {
            FileWriter writer = new FileWriter(countriesFilePath + LocalDateTime.now() + countriesFileExtension);
//            for (CountryDTO country: countries) {
//                writer.write(country.getCountryId() + ";" + country.getName() + ";" + country.getCapital() + "\n");
//            };

            writer.close();
            log.info("[saveCountryListToFile][countries saved successfully to file!]");
        }
        catch (IOException e) {
            log.error("[saveCountryListToFile][Error: " + e + "]");
        }
    }

}
