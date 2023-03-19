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

    public static String fixWorldBankUrl(String baseUrl, String countryName) {

        if (countryName.contains("Bolivia")) {
            countryName = "bolivia";
        }
        else if (countryName.contains("Congo") && !countryName.contains("Democratic")) {
            countryName = "congo-rep";
        }
        else if (countryName.contains("Ivoire")) {
            countryName = "cote-divoire";
        }
        else if (countryName.contains("Gambia")) {
            countryName = "gambia-the";
        }
        else if (countryName.contains("People") && countryName.contains("Korea")) {
            countryName = "korea-dem-peoples-rep";
        }
        else if (countryName.contains("Democratic") && countryName.contains("Congo")) {
            countryName = "congo-dem-rep";
        }
        else if (countryName.contains("Egypt")) {
            countryName = "egypt-arab-rep";
        }
        else if (countryName.contains("Iran")) {
            countryName = "iran-islamic-rep";
        }
        else if (countryName.contains("Kyrgyzstan")) {
            countryName = "kyrgyz-republic";
        }
        else if (countryName.contains("Lao") && countryName.contains("People")) {
            countryName = "lao-pdr";
        }
        else if (countryName.contains("Micronesia")) {
            countryName = "micronesia-fed-sts";
        }
        else if (countryName.contains("Moldova")) {
            countryName = "moldova";
        }
        else if (!countryName.contains("People") && countryName.contains("Korea")) {
            countryName = "korea-rep";
        }
        else if (countryName.contains("Kitts")) {
            countryName = "st-kitts-and-nevis";
        }
        else if (countryName.contains("Saint Lucia")) {
            countryName = "st-lucia";
        }
        else if (countryName.contains("Grenadines")) {
            countryName = "st-vincent-and-the-grenadines";
        }
        else if (countryName.contains("Slovakia")) {
            countryName = "slovak-republic";
        }
        else if (countryName.contains("United Kingdom")) {
            countryName = "united-kingdom";
        }
        else if (countryName.contains("Tanzania")) {
            countryName = "tanzania";
        }
        else if (countryName.contains("United States")) {
            countryName = "united-states";
        }
        else if (countryName.contains("Venezuela")) {
            countryName = "venezuela-rb";
        }
        else if (countryName.contains("Viet Nam")) {
            countryName = "vietnam";
        }
        else if (countryName.contains("Yemen")) {
            countryName = "yemen-rep";
        }
        else {
            countryName = countryName.trim().replace(' ', '-');
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

    public static void parseCountryDetailsFromWorldBank(CountryDTO countryDTO, Elements asideElement) {
        Elements liElements = asideElement.select("ul > li");
        if (liElements != null) {
            for (Element element : liElements) {
                if (element.html().contains("Region")) {
                    Element anchorRegion = element.select("a").first();
                    countryDTO.setRegion(anchorRegion.text());
                }
                else if (element.html().contains("Income level")) {
                    Element anchorIncome = element.select("a").first();
                    countryDTO.setIncomeCategory(anchorIncome.text());
                }
            }
        }
    }

}
