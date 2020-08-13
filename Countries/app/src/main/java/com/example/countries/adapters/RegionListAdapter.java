package com.example.countries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegionListAdapter extends RecyclerView.Adapter<RegionListAdapter.RegionViewHolder> {
    private Context mContext;
    private List<String> regions;
    private ItemClickListener itemClickListener;

    public RegionListAdapter(Context context, List<String> regions, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.regions = regions;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_item, parent, false);
        RegionViewHolder viewHolder = new RegionViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegionViewHolder holder, int position) {
        holder.bindTo(regions.get(position));
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.region_frame)
        FrameLayout frameLayout;

        @BindView(R.id.region_name)
        TextView textView;

        public RegionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, regions.get(getAdapterPosition()));
                }
            });
        }

        public void bindTo(String name) {
            textView.setText(name);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, String region);
    }
}
