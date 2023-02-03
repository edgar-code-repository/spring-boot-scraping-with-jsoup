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
public class CountryWikipediaRepositoryImpl implements CountryScrapingRepository {

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
                            .countryId(contador)
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
            log.error("[getCountries] [Error: " + e + "]");
        }

        log.info("[getCountries] [countries list size: " +  countryList.size() + "]");
        return countryList;
    }

    @Override
    public CountryDTO getCountryDetails(CountryDTO countryDTO) {
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
