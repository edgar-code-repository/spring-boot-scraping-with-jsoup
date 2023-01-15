package com.example.scraping.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountryScraping implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.debug("Running CountryScraping on startup...");
    }
}
