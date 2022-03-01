package com.college.portal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.model.ContactUs;
import com.college.portal.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private List<News> mList;

    public NewsAdapter(Context mContext, List<News> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_news, parent, false);
        NewsAdapter.ViewHolder holder = new NewsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        holder.newsTitle.setText(mList.get(position).getnTitle());
        holder.newsMessage.setText(mList.get(position).getnMessage());
        holder.newsDate.setText(mList.get(position).getnDate());

        holder.newsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "News/Event : " + mList.get(position).getnId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle, newsMessage, newsDate, newsClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.news_title);
            newsMessage = itemView.findViewById(R.id.news_message);
            newsDate = itemView.findViewById(R.id.news_date);
            newsClick = itemView.findViewById(R.id.news_click);

        }
    }
}
