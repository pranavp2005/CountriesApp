package com.example.countries;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.countries.adapters.RegionListAdapter;
import com.example.countries.helpers.CountryDataFetcher;
import com.example.countries.helpers.CountryDataFetcherImpl;
import com.example.countries.helpers.RegionDataHandler;
import com.example.countries.tasks.FetchRegionDataTask;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RegionActivity extends AppCompatActivity implements RegionDataHandler, RegionListAdapter.ItemClickListener {

    @BindView(R.id.region_list)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        CountryDataFetcher countryDataFetcher = CountryDataFetcherImpl.getInstance(this);

        new FetchRegionDataTask(countryDataFetcher, this).execute();
    }

    private boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void handleRegionData(Set<String> regions) {
        progressBar.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RegionListAdapter regionListAdapter = new RegionListAdapter(this, new ArrayList<String>(regions), this);
        recyclerView.setAdapter(regionListAdapter);

    }

    @Override
    public void onItemClick(View view, String region) {
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra("region", region);
        startActivity(intent);
    }
}