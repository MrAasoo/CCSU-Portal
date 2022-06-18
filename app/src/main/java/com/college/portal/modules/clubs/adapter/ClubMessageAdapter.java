package com.college.portal.modules.clubs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.modules.clubs.model.ClubChats;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.sharedpreferences.SharedPrefManager;

import java.util.List;

public class ClubMessageAdapter extends RecyclerView.Adapter<ClubMessageAdapter.ViewHolder> {

    private final Context mContext;
    private final List<ClubChats> mList;

    public ClubMessageAdapter(Context mContext, List<ClubChats> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        StudentPref pref = SharedPrefManager.getInstance(mContext).getStudentInfo();

        ClubChats chat = mList.get(position);
        if (chat.getStd_id().equals(pref.getStdId())) {
            holder.send.setVisibility(View.VISIBLE);
            holder.receive.setVisibility(View.GONE);
            holder.stdNameSend.setText(chat.getStd_name());
            holder.messageSend.setText(chat.getMessage());
            holder.timeSend.setText(chat.getTime());
            holder.dateSend.setText(chat.getDate());
        } else {
            holder.send.setVisibility(View.GONE);
            holder.receive.setVisibility(View.VISIBLE);
            holder.stdName.setText(chat.getStd_name());
            holder.message.setText(chat.getMessage());
            holder.time.setText(chat.getTime());
            holder.date.setText(chat.getDate());
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stdName, time, date, message, stdNameSend, timeSend, dateSend, messageSend;
        RelativeLayout receive, send;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            receive = itemView.findViewById(R.id.receive);
            send = itemView.findViewById(R.id.sender);

            //receive
            stdName = itemView.findViewById(R.id.std_name);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.chat_message);

            //send
            stdNameSend = itemView.findViewById(R.id.std_name_send);
            timeSend = itemView.findViewById(R.id.time_send);
            dateSend = itemView.findViewById(R.id.date_send);
            messageSend = itemView.findViewById(R.id.chat_message_send);

        }
    }
}
