package com.example.coronatracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CountryAdapter extends ArrayAdapter<CountryModal> {

    private final List<CountryModal> countryModalList;
    private List<CountryModal> countryModalListFiltered;
    private Context context;

    public CountryAdapter(@NonNull Context context, List<CountryModal> countryModalList) {
        super(context, R.layout.custom_list_item,countryModalList);

        this.context = context;
        this.countryModalList = countryModalList;
        this.countryModalListFiltered = countryModalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item,null,true);
        TextView country = view.findViewById(R.id.tvCountry);
        ImageView flag = view.findViewById(R.id.imageFlag);

        country.setText(countryModalListFiltered.get(position).getCountry());
        Glide.with(getContext()).load(countryModalListFiltered.get(position).getFlag()).into(flag);

        return view;
    }

    @Override
    public int getCount() {
        return countryModalListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModal getItem(int position) {
        return countryModalListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModalList.size();
                    filterResults.values = countryModalList;

                }else{
                    List<CountryModal> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModal itemsModel:countryModalList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModalListFiltered = (List<CountryModal>) results.values;
                AffactedCountries.countryModalList = (List<CountryModal>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
