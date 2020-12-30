package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvContinent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffactedCountries.countryModalList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvContinent = findViewById(R.id.tvContinent);

        tvCountry.setText(AffactedCountries.countryModalList.get(positionCountry).getCountry());
        tvCases.setText(AffactedCountries.countryModalList.get(positionCountry).getCases());
        tvRecovered.setText(AffactedCountries.countryModalList.get(positionCountry).getRecovered());
        tvCritical.setText(AffactedCountries.countryModalList.get(positionCountry).getCritical());
        tvActive.setText(AffactedCountries.countryModalList.get(positionCountry).getActive());
        tvTodayCases.setText(AffactedCountries.countryModalList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(AffactedCountries.countryModalList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(AffactedCountries.countryModalList.get(positionCountry).getTodayDeaths());
        tvContinent.setText(AffactedCountries.countryModalList.get(positionCountry).getContinent());
    }
}