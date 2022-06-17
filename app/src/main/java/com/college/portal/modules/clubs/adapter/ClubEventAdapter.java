package com.college.portal.modules.clubs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.modules.clubs.ClubEventActivity;
import com.college.portal.modules.clubs.model.ClubEvents;

import java.util.List;

public class ClubEventAdapter extends RecyclerView.Adapter<ClubEventAdapter.ViewHolder> {

    private final Context mContext;
    private final List<ClubEvents> mList;

    public ClubEventAdapter(Context mContext, List<ClubEvents> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ClubEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_club_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubEventAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        ClubEvents clubEvent = mList.get(position);

        holder.eventTopic.setText(clubEvent.getEventTopic());
        holder.eventMotive.setText(clubEvent.getEventMotive());
        holder.eventPostDate.setText(String.format(mContext.getString(R.string.event_post_date), clubEvent.getEventPostDate()));

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClubEventActivity.class);
                intent.putExtra(AppApi.CLUB_EVENT_ID, mList.get(holder.getAdapterPosition()).getEventId());
                intent.putExtra(AppApi.CLUB_ID, mList.get(holder.getAdapterPosition()).getClubId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventTopic, eventMotive, eventPostDate;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHolder = itemView.findViewById(R.id.holder);
            eventTopic = itemView.findViewById(R.id.event_topic);
            eventMotive = itemView.findViewById(R.id.event_motive);
            eventPostDate = itemView.findViewById(R.id.event_post_date);

        }
    }
}
