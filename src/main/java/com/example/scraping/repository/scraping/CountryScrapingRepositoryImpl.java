package com.example.scraping.repository.scraping;

import com.example.scraping.dto.CountryDTO;
import com.example.scraping.util.CountryScrapingUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CountryScrapingRepositoryImpl implements CountryScrapingRepository {

    @Value("${urlCountriesUnitedNations}")
    private String urlCountriesUnitedNations;

    @Value("${baseUrlWikipedia}")
    private String baseUrlWikipedia;

    @Override
    public List<CountryDTO> getCountries() {
        log.info("[getCountries] [Calling URL: " + urlCountriesUnitedNations + "]");

        List<CountryDTO> countryList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlCountriesUnitedNations).get();
            Elements cardsCountries = doc.select("div.card");

            int count = 1;
            for(Element card: cardsCountries) {
                Element countryElement = card.select("h2").first();

                CountryDTO country = new CountryDTO();
                country.setCountryId(count);
                country.setName(countryElement.text());

                String urlWikipedia = CountryScrapingUtil.fixWikipediaUrl(baseUrlWikipedia, countryElement.text());
                country.setUrlWikipedia(urlWikipedia);

                countryList.add(country);
                count++;
            }
        }
        catch (Exception e) {
            log.error("[getCountries] [Error: " + e + "]");
        }

        log.info("[getCountries] [countries list size: " +  countryList.size() + "]");
        return countryList;
    }

    @Override
    public CountryDTO getCountryDetailsFromWikipedia(CountryDTO countryDTO) {
        log.info("[getCountryDetailsFromWikipedia] [" + countryDTO.toString() + "]");
        try {
            Document doc = Jsoup.connect(countryDTO.getUrlWikipedia()).get();
            if (doc == null) return null;
            Elements infoBox = doc.select("table.infobox");
            if (infoBox == null) return null;

            CountryScrapingUtil.parseCapital(countryDTO, infoBox);
            CountryScrapingUtil.parseOfficialName(countryDTO, infoBox);

            log.info("[getCountryDetailsFromWikipedia][official name: " + countryDTO.getOfficialName() + "]");
            log.info("[getCountryDetailsFromWikipedia][capital: " + countryDTO.getCapital() + "]");
        }
        catch (Exception e) {
            log.error("[getCountryDetailsFromWikipedia][Error: " + e.toString() + "]");
        }
        return countryDTO;
    }


}
