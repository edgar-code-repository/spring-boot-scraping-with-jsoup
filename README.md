SCRAPING DATA USING JSOUP LIBRARY
---------------------------------------------------------------------------

Spring Boot application that retrieves info about countries by scraping data 
from World Bank, United Nations and Wikipedia.

---------------------------------------------------------------------------

Jsoup maven dependency added in pom.xml file:

```
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.10.2</version>
    </dependency>
```

---------------------------------------------------------------------------

A list with country members of United Nations is retrieved from this URL:

```
https://www.un.org/en/about-us/member-states

```

In the previous URL there is a list with 193 countries:

![CountriesList](screenshots/Member_States_United_Nations.png)


---------------------------------------------------------------------------

Once the application runs, the list of countries is retrieved and stored
in an H2 database:

![tbl_country](screenshots/tbl_country_in_H2.png)

---------------------------------------------------------------------------