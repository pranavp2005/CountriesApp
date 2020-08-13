package com.example.countries.tasks;

import android.os.AsyncTask;

import com.example.countries.helpers.CountryDataFetcher;
import com.example.countries.helpers.RegionDataHandler;

import java.util.Set;

public class FetchRegionDataTask extends AsyncTask<Void, Void, Set<String>> {

    private RegionDataHandler regionDataHandler;
    private CountryDataFetcher countryDataFetcher;


    public FetchRegionDataTask(CountryDataFetcher countryDataFetcher, RegionDataHandler regionDataHandler) {
        this.countryDataFetcher = countryDataFetcher;
        this.regionDataHandler = regionDataHandler;
    }
    @Override
    protected Set<String> doInBackground(Void... strings) {
        return countryDataFetcher.fetchRegions();
    }

    @Override
    protected void onPostExecute(Set<String> regions) {
        regionDataHandler.handleRegionData(regions);
    }
}
