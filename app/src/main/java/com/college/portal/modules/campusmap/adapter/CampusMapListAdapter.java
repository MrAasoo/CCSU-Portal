package com.college.portal.modules.campusmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.modules.campusmap.CampusMapActivity;
import com.college.portal.modules.campusmap.model.CampusMap;

import java.util.List;

public class CampusMapListAdapter extends RecyclerView.Adapter<CampusMapListAdapter.ViewHolder> {
    private final Context mContext;
    private final List<CampusMap> mList;

    public CampusMapListAdapter(Context mContext, List<CampusMap> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CampusMapListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_map_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampusMapListAdapter.ViewHolder holder, int position) {

        holder.locationName.setText(mList.get(position).getLocationName());
        holder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CampusMapActivity.class);
                intent.putExtra("title", mList.get(holder.getAdapterPosition()).getLocationName());
                intent.putExtra("latitude", mList.get(holder.getAdapterPosition()).getLocationLatitude());
                intent.putExtra("longitude", mList.get(holder.getAdapterPosition()).getLocationLongitude());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView locationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.location_name);
        }
    }
}
