package com.example.scraping.repository.file;

import com.example.scraping.dto.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
public class CountryFileRepositoryImpl implements CountryFileRepository {

    @Value("${countriesFilePath}")
    private String countriesFilePath;

    @Value("${countriesFileExtension}")
    private String countriesFileExtension;

    public void saveCountryList(List<CountryDTO> countries) {
        log.info("[saveCountryList][Saving countries to file...]");
        try {
            FileWriter writer = new FileWriter(countriesFilePath + LocalDateTime.now() + countriesFileExtension);
            for (CountryDTO country: countries) {
                writer.write(country.getCountryId() + ";" + country.getName() + ";" + country.getOfficialName() + ";" + country.getCapital() + "\n");
            };
            writer.close();
            log.info("[saveCountryList][countries saved successfully to file!]");
        }
        catch (IOException e) {
            log.error("[saveCountryList][Error: " + e + "]");
        }
    }

}
