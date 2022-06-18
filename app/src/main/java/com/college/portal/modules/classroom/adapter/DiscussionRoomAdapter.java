package com.college.portal.modules.classroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.modules.classroom.ClassroomMessagesActivity;
import com.college.portal.modules.classroom.module.DiscussionRoom;

import java.util.List;

public class DiscussionRoomAdapter extends RecyclerView.Adapter<DiscussionRoomAdapter.ViewHolder> {

    private final Context mContext;
    private final List<DiscussionRoom> mList;

    public DiscussionRoomAdapter(Context mContext, List<DiscussionRoom> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DiscussionRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_discussion_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionRoomAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        DiscussionRoom room = mList.get(position);

        holder.roomName.setText(room.getRoomName());

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassroomMessagesActivity.class);
                intent.putExtra(AppApi.ROOM_KEY, room.getRoomKey());
                intent.putExtra(AppApi.ROOM_NAME, room.getRoomName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardHolder;
        TextView roomName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHolder = itemView.findViewById(R.id.holder);
            roomName = itemView.findViewById(R.id.room_name);

        }
    }


}
