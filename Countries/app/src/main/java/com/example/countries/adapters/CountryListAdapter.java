package com.example.countries.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.countries.R;
import com.example.countries.db.Country;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {
    private Context mContext;
    private List<Country> countries;
    private Activity activity;

    public CountryListAdapter(Context context, List<Country> countries, Activity activity) {
        this.mContext = context;
        this.countries = countries;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bindTo(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.country_frame)
        FrameLayout frameLayout;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.capital)
        TextView capital;

        @BindView(R.id.flag)
        ImageView flag;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Country country) {
            name.setText(country.getName());
            capital.setText(country.getCapital());
            try {
                SvgLoader.pluck()
                        .with(activity)
                        .setPlaceHolder(R.drawable.appiconglobe, R.drawable.appiconglobe)
                        .load(country.getFlagUrl(), flag);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

