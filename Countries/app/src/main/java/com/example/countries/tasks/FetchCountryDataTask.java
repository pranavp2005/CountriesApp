package com.example.countries.tasks;

import android.os.AsyncTask;

import com.example.countries.db.Country;
import com.example.countries.helpers.CountryDataFetcher;
import com.example.countries.helpers.CountryDataHandler;

import java.util.List;

public class FetchCountryDataTask  extends AsyncTask<String, Void, List<Country>> {

    private CountryDataHandler countryDataHandler;
    private CountryDataFetcher countryDataFetcher;


    public FetchCountryDataTask(CountryDataFetcher countryDataFetcher, CountryDataHandler countryDataHandler) {
        this.countryDataFetcher = countryDataFetcher;
        this.countryDataHandler = countryDataHandler;
    }
    @Override
    protected List<Country> doInBackground(String... strings) {
        return countryDataFetcher.fetchCountriesForRegion(strings[0]);
    }

    @Override
    protected void onPostExecute(List<Country> countryList) {
        countryDataHandler.handleCountryData(countryList);
    }
}
