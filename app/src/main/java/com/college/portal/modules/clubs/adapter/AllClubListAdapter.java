package com.college.portal.modules.clubs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.modules.clubs.ClubPageActivity;
import com.college.portal.modules.clubs.model.Club;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllClubListAdapter extends RecyclerView.Adapter<AllClubListAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Club> mList;

    public AllClubListAdapter(Context mContext, List<Club> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_club, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Card Animation
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        Club club = mList.get(position);

        // Values
        holder.clubName.setText(club.getClubName());
        holder.clubDate.setText(String.format(mContext.getString(R.string.created_on_place_holder), club.getClubStartDate()));
        if (!club.getClubLogo().equals("null"))
            Picasso.get().load(RetroApi.CLUB_LOGO + club.getClubLogo()).placeholder(R.drawable.club_logo_placeholder).into(holder.logoImage);
        else
            holder.logoImage.setImageResource(R.drawable.club_logo_placeholder);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), ClubPageActivity.class);
                intent.putExtra(AppApi.CLUB_ID, club.getClubId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView logoImage;
        TextView clubName, clubDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            logoImage = itemView.findViewById(R.id.club_logo);
            clubName = itemView.findViewById(R.id.club_name);
            clubDate = itemView.findViewById(R.id.club_created);

        }
    }
}
