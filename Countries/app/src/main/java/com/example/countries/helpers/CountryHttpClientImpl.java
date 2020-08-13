package com.example.countries.helpers;

import com.example.countries.db.Country;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class CountryHttpClientImpl implements CountryHttpClient {

    private OkHttpClient httpClient;
    private static final String CACHE_PATH= "/data/data/countries/cache";
    private final static String COUNTRY_INFO_URL = "https://restcountries.eu/rest/v2/all";


    public CountryHttpClientImpl() {
        httpClient = getHttpClient();
    }

    @Override
    public List<Country> getAllCountries() {
        try {
            Response response = getHttpClient().newCall(getRequest(COUNTRY_INFO_URL)).execute();
            String responseString = IOUtils.toString(response.body().byteStream(), Charset.defaultCharset());
            JSONArray responseJson = new JSONArray(responseString);
            List<Country> countries = new ArrayList<>();
            for (int i = 0; i < responseJson.length(); i++) {
                JSONObject jsonObject = responseJson.getJSONObject(i);
                String name = jsonObject.getString("name");
                String region = jsonObject.getString("region");
                String capital = jsonObject.getString("capital");
                String flagUrl = jsonObject.getString("flag");
                countries.add(new Country(name, region, capital, flagUrl));
            }
            return countries;
        } catch (IOException| JSONException e) {
            return new ArrayList<>();
        }
    }

    private static Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .cacheControl(new CacheControl.Builder().maxAge(3, TimeUnit.DAYS).build())
                .build();
    }

    private OkHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(new Cache(new File(CACHE_PATH), 20*1024*1024))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return httpClient;
    }
}
