package com.example.scraping.repository;

import com.example.scraping.dto.CountryDTO;
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
public class CountryWikipediaRepository implements CountryScrapingRepository {

    @Value("${unCountriesWikipedia}")
    private String countriesURL;

    @Override
    public List<CountryDTO> getCountries() {
        log.info("[getCountries] [Calling URL: " + countriesURL + "]");

        List<CountryDTO> countryList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(countriesURL).get();
            Elements tableRows = doc.select("table > tbody > tr > th > a");

            log.info("[getCountries] [Parsing countries...]");

            int contador = 1;
            for(Element element: tableRows) {
                if (contador <= 193) {
                    CountryDTO country = CountryDTO.builder()
                            .id(contador)
                            .name(element.text())
                            .url(element.attr("abs:href"))
                            .build();

                    log.info(country.toString());

                    countryList.add(country);
                    contador++;
                }
            }
        }
        catch (Exception e) {
            log.error("[getCountries] [Error: " + e.toString() + "]");
        }

        log.info("[getCountries] [countries list size: " +  countryList.size() + "]");
        return countryList;
    }

}
