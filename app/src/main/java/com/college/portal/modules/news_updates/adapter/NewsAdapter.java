package com.college.portal.modules.news_updates.adapter;

import static com.college.portal.api.AppApi.NEWS_ID;

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
import com.college.portal.modules.news_updates.NewsActivity;
import com.college.portal.modules.news_updates.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<News> mList;

    public NewsAdapter(Context mContext, List<News> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));


        holder.newsTitle.setText(mList.get(position).getnTitle());
        holder.newsMessage.setText(mList.get(position).getnSubtitle());
        holder.newsDate.setText(String.format(mContext.getString(R.string.date_place_holder), mList.get(position).getnDate()));

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra(NEWS_ID, mList.get(holder.getAdapterPosition()).getnId());
                mContext.startActivity(intent);
                // Toast.makeText(mContext, "News/Event : " + mList.get(holder.getAdapterPosition()).getnId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle, newsMessage, newsDate;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHolder = itemView.findViewById(R.id.holder);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsMessage = itemView.findViewById(R.id.news_subtitle);
            newsDate = itemView.findViewById(R.id.news_date);

        }
    }
}
