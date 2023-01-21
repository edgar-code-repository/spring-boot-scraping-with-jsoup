package com.example.scraping.component;

import com.example.scraping.dto.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountryScraping implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Running CountryScraping on startup...");

        String countriesUrl = "https://en.wikipedia.org/wiki/Member_states_of_the_United_Nations";

        log.info("Calling URL: " + countriesUrl);

        Document doc = Jsoup.connect(countriesUrl).get();
        Elements tableRows = doc.select("table > tbody > tr > th > a");

        log.info("Parsing UN country members...");

        int contador = 1;
        for(Element element: tableRows) {
            if (contador <= 193) {
                CountryDTO country = CountryDTO.builder()
                        .id(contador)
                        .name(element.text())
                        .url(element.attr("abs:href"))
                        .build();

                log.info(country.toString());
                contador++;
            }
        }

    }
}
