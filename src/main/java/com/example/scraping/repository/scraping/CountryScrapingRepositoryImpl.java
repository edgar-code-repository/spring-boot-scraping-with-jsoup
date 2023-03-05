package com.example.scraping.repository.scraping;

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
public class CountryScrapingRepositoryImpl implements CountryScrapingRepository {

    @Value("${urlCountriesUnitedNations}")
    private String urlCountriesUnitedNations;

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
                country.setName(countryElement.text());

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
        log.info("[getCountry] [Name: " + countryDTO.getName() + "]");

        try {
            Document docCountry = Jsoup.connect(countryDTO.getUrl()).get();
            Elements tableRowsCountry = docCountry.select("table > tbody > tr");
            for (Element row : tableRowsCountry) {
                if (row.text().contains("Capital") && countryDTO.getCapital() == null) {
                    String capital = "";

                    Elements anchorCapital = row.getElementsByTag("a");
                    if (!anchorCapital.text().trim().equals("")) {
                        if (anchorCapital.size() == 1 && !anchorCapital.get(0).html().contains("geo-default")
                                && !anchorCapital.get(0).html().contains("Coordinates")
                                && !anchorCapital.get(0).html().contains("[")) {
                            capital = anchorCapital.get(0).html();
                        }
                        else if (anchorCapital.size() > 0) {
                            for (int index = 0; index < anchorCapital.size(); index++) {
                                if (!anchorCapital.get(index).html().contains("geo-default")
                                        && !anchorCapital.get(index).html().contains("Coordinates")
                                        && !anchorCapital.get(index).html().contains("[")) {
                                    if (index == 0)
                                        capital = anchorCapital.get(index).html();
                                    else
                                        capital = capital + " - " + anchorCapital.get(index).html();
                                }
                            }
                        }
                    }
                    countryDTO.setCapital(capital);
                }
            }
        }
        catch (Exception e) {
            log.error("[getCountry] [Error: " + e + "]");
        }

        return countryDTO;
    }


}
