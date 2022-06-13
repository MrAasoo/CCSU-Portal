package com.college.portal.modules.adapter;

import static com.college.portal.api.AppApi.NEWS_ID;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.college.portal.R;
import com.college.portal.api.RetroApi;
import com.college.portal.modules.NewsActivity;
import com.college.portal.modules.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsSliderAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<News> mNewsSlideList;

    public NewsSliderAdapter(Context mContext, List<News> mNewsSlideList) {
        this.mContext = mContext;
        this.mNewsSlideList = mNewsSlideList;
    }

    @Override
    public int getCount() {
        return mNewsSlideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_news_viewpager, null, false);

        ImageView news_image = view.findViewById(R.id.pager_image);
        TextView news_title = view.findViewById(R.id.pager_title);
        TextView news_subtitle = view.findViewById(R.id.pager_subtitle);

        news_title.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation_long));
        news_subtitle.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation_long));

        news_title.setText(mNewsSlideList.get(position).getnTitle());
        news_subtitle.setText(mNewsSlideList.get(position).getnSubtitle());

        Picasso.get().load(RetroApi.NEWS_IMAGES + mNewsSlideList.get(position).getnImage()).placeholder(R.drawable.place_holder).into(news_image);

        news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra(NEWS_ID, mNewsSlideList.get(position).getnId());
                mContext.startActivity(intent);
                // Toast.makeText(mContext, "News/Event : " + mNewsSlideList.get(position).getnId(), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }
}
