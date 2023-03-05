package com.example.scraping.util;

import com.example.scraping.dto.CountryDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CountryScrapingUtil {

    public static String fixWikipediaUrl(String baseUrl, String countryName) {
        countryName = countryName.trim().replace(' ', '_');

        if (countryName.contains("Ivoire")) {
            countryName = "Ivory_Coast";
        }
        else if (countryName.contains("People") && countryName.contains("Korea")) {
            countryName = "North_Korea";
        }
        else if (countryName.contains("Democratic") && countryName.contains("Congo")) {
            countryName = "Democratic_Republic_of_the_Congo";
        }
        else if (countryName.contains("Congo")) {
            countryName = "Republic_of_the_Congo";
        }
        else if (countryName.contains("Gambia")) {
            countryName = "The_Gambia";
        }
        else if (countryName.contains("Georgia")) {
            countryName = "Georgia_(country)";
        }
        else if (countryName.contains("Ireland")) {
            countryName = "Republic_of_Ireland";
        }
        else if (countryName.contains("Lao") && countryName.contains("People")) {
            countryName = "Laos";
        }

        return baseUrl + "/" + countryName;
    }

    public static void parseCapital(CountryDTO countryDTO, Elements infoBox) {
        if (countryDTO.getName().equals("Singapore")) {
            countryDTO.setCapital("Singapore");
        }
        else {
            Elements rowsCountryTable = infoBox.select("tr");
            if (rowsCountryTable == null) return;
            for (Element row : rowsCountryTable) {
                if (row.text().contains("Capital")) {
                    Element capitalCell = row.select(".infobox-data").first();
                    Element capitalAnchor = capitalCell.select("a").first();
                    Elements capitalList = capitalCell.select("ul > li");
                    if (capitalList != null && capitalList.size() > 0) {
                        for (Element elementCapital : capitalList) {
                            if (!elementCapital.text().contains("None")) {
                                countryDTO.setCapital(elementCapital.text());
                            }
                        }
                    } else {
                        countryDTO.setCapital(capitalAnchor.text());
                    }

                }
            }
        }
    }

    public static void parseOfficialName(CountryDTO countryDTO, Elements infoBox) {
        Elements classCountryName = infoBox.select(".country-name");
        Elements cellCountryName = infoBox.select("th.infobox-above");
        if (classCountryName != null && classCountryName.size() > 0)  {
            countryDTO.setOfficialName(classCountryName.first().text());
        }
        else if (cellCountryName != null && cellCountryName.size() > 0) {
            countryDTO.setOfficialName(cellCountryName.select("div.fn").text());
        }
    }
}
