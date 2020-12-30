package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffactedCountries extends AppCompatActivity {

    EditText search;
    ProgressBar progressBar;
    ListView listView;

    public static List<CountryModal> countryModalList = new  ArrayList<>();
    CountryModal countryModal;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affacted_countries);

        search = findViewById(R.id.etSearch);
        progressBar = findViewById(R.id.countryProgress);
        listView = findViewById(R.id.countryList);

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailsActivity.class).putExtra("position",position));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter.getFilter().filter(s);
                countryAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void fetchData() {

        String url = "https://disease.sh/v3/covid-19/countries";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");
                                String continent = jsonObject.getString("continent");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                countryModal = new CountryModal(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,critical,active,continent);
                                countryModalList.add(countryModal);
                            }

                            countryAdapter = new CountryAdapter(getApplicationContext(),countryModalList);
                            listView.setAdapter(countryAdapter);
                            progressBar.setVisibility(View.GONE);

                        }catch (JSONException exception){
                            exception.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}