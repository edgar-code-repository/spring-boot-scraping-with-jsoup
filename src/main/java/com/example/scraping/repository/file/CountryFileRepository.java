package com.example.scraping.repository.file;

import com.example.scraping.dto.CountryDTO;

import java.util.List;

public interface CountryFileRepository {

    void saveCountryList(List<CountryDTO> countries);

}
