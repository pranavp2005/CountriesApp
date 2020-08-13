package com.example.countries.helpers;

import com.example.countries.db.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CountryDataFetcher {

    Set<String> fetchRegions();

    List<Country> fetchCountriesForRegion(String region);
}
