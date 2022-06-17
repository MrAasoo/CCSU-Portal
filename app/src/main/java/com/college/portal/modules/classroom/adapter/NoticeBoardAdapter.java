package com.college.portal.modules.classroom.adapter;

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
import com.college.portal.modules.classroom.NoticeBoardActivity;
import com.college.portal.modules.classroom.module.NoticeBoard;

import java.util.List;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder> {

    private final Context mContext;
    private final List<NoticeBoard> mList;

    public NoticeBoardAdapter(Context mContext, List<NoticeBoard> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public NoticeBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_notice_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeBoardAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        NoticeBoard notice = mList.get(position);

        holder.noticeTitle.setText(notice.getNoticeTitle());
        holder.noticeMessage.setText(notice.getNoticeMessage());
        holder.noticeDate.setText(String.format(mContext.getString(R.string.date_place_holder), notice.getNoticeDate()));

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoticeBoardActivity.class);
                intent.putExtra(AppApi.NOTICE_ID, mList.get(holder.getAdapterPosition()).getNoticeId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView noticeTitle, noticeMessage, noticeDate;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHolder = itemView.findViewById(R.id.holder);
            noticeTitle = itemView.findViewById(R.id.notice_title);
            noticeMessage = itemView.findViewById(R.id.notice_message);
            noticeDate = itemView.findViewById(R.id.notice_date);

        }
    }
}
