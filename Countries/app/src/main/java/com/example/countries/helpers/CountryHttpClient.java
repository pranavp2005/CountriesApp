package com.example.countries.helpers;

import com.example.countries.db.Country;

import java.util.List;

public interface CountryHttpClient {
    List<Country> getAllCountries();
}
