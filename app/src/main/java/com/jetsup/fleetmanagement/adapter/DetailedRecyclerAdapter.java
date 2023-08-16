package com.jetsup.fleetmanagement.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jetsup.fleetmanagement.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedRecyclerAdapter extends RecyclerView.Adapter<DetailedRecyclerAdapter.DetailedViewHolder> {
    @NonNull
    @Override
    public DetailedRecyclerAdapter.DetailedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedRecyclerAdapter.DetailedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DetailedViewHolder extends RecyclerView.ViewHolder {
        CardView detailedCardView;
        CircleImageView detailedVehicleImage;
        TextView detailNumberPlate, detailDriverName, detailLatitude, detailLongitude;

        public DetailedViewHolder(@NonNull View itemView) {
            super(itemView);
            detailedCardView = itemView.findViewById(R.id.detailCardView);
            detailedVehicleImage = itemView.findViewById(R.id.detailVehicleImage);
            detailDriverName = itemView.findViewById(R.id.detailDriverName);
            detailNumberPlate = itemView.findViewById(R.id.detailNumberPlate);
            detailLatitude = itemView.findViewById(R.id.detailLatitude);
            detailLongitude = itemView.findViewById(R.id.detailLongitude);
            // set on click listener for the card view
        }
    }
}
