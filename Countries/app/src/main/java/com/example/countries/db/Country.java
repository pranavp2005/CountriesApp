package com.example.countries.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Country {

    @NonNull
    @PrimaryKey
    public String id;

    public String name;

    public String region;

    public String capital;

    public String flagUrl;

    public Country(String name, String region, String capital, String flagUrl) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.region = region;
        this.capital = capital;
        this.flagUrl = flagUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
