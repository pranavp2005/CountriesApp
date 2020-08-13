package com.example.countries.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.FAIL;

@Dao
public interface CountryDao {

    @Query("SELECT DISTINCT region from Country")
    List<String> getDistinctRegions();

    @Query("SELECT * from Country where region = :region")
    List<Country> getCountriesByRegion(String region);

    @Insert(onConflict = FAIL)
    void insertCountry(Country country);
}
