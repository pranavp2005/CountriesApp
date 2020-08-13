package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.countries.adapters.CountryListAdapter;
import com.example.countries.adapters.RegionListAdapter;
import com.example.countries.db.Country;
import com.example.countries.helpers.CountryDataFetcher;
import com.example.countries.helpers.CountryDataFetcherImpl;
import com.example.countries.helpers.CountryDataHandler;
import com.example.countries.tasks.FetchCountryDataTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CountryActivity extends AppCompatActivity implements CountryDataHandler {

    @BindView(R.id.country_list)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String region = getIntent().getStringExtra("region");
        getSupportActionBar().setTitle(region);
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);

        CountryDataFetcher countryDataFetcher = CountryDataFetcherImpl.getInstance(this);

        new FetchCountryDataTask(countryDataFetcher, this).execute(region);
    }

    @Override
    public void handleCountryData(List<Country> countryList) {
        recyclerView.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CountryListAdapter countryListAdapter = new CountryListAdapter(this, countryList, this);
        recyclerView.setAdapter(countryListAdapter);
    }
}