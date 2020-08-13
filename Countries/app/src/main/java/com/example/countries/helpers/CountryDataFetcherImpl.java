package com.example.countries.helpers;

import android.content.Context;

import com.example.countries.db.AppDatabase;
import com.example.countries.db.Country;
import com.example.countries.db.CountryDao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class CountryDataFetcherImpl implements CountryDataFetcher {

    private AppDatabase appDatabase;
    private CountryHttpClient countryHttpClient;
    private static CountryDataFetcher INSTANCE;
    private static final String CACHE_PATH= "/data/data/countries/cache";


    private CountryDataFetcherImpl(Context context) {
        this.appDatabase = AppDatabase.getDatabase(context);
        this.countryHttpClient = new CountryHttpClientImpl();
    }

    public static CountryDataFetcher getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CountryDataFetcherImpl(context);
        }
        return INSTANCE;
    }

    @Override
    public Set<String> fetchRegions() {
        CountryDao countryDao = appDatabase.countryModel();
        List<String> regionList = countryDao.getDistinctRegions();
        Set<String> regions = new HashSet<>();
        if (regionList == null || regionList.isEmpty()) {
           List<Country> countries = countryHttpClient.getAllCountries();
           for (Country country : countries) {
               countryDao.insertCountry(country);
               regions.add(country.getRegion());
           }
        } else {
           regions.addAll(regionList);
        }
        return regions;
    }

    @Override
    public List<Country> fetchCountriesForRegion(String region) {
        CountryDao countryDao = appDatabase.countryModel();
        List<Country> countries = countryDao.getCountriesByRegion(region);
        if (countries == null || countries.isEmpty()) {
            List<Country> allCountries = countryHttpClient.getAllCountries();
            for (Country country : allCountries) {
                countryDao.insertCountry(country);
                if(country.getRegion().equals(region))
                    countries.add(country);
            }
        }
        return countries;
    }
}
