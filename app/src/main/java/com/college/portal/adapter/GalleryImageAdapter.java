package com.college.portal.adapter;

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
import com.college.portal.activity.CollegeGalleryImageActivity;
import com.college.portal.api.AppApi;
import com.college.portal.model.Gallery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ViewHolder> {
    private Context mContext;
    private List<Gallery> mList;

    public GalleryImageAdapter(Context mContext, List<Gallery> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        // Values
        holder.imageDescription.setText(mList.get(position).getMediaDescription());
        holder.postDate.setText(String.format(mContext.getString(R.string.post_date_placeholder), mList.get(position).getMediaAdded()));

        Picasso.get().load(mList.get(position).getMediaPath()).placeholder(R.drawable.place_holder_image).into(holder.galleryImage);

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CollegeGalleryImageActivity.class);
                intent.putExtra(AppApi.MEDIA_PATH, mList.get(holder.getAdapterPosition()).getMediaPath());
                intent.putExtra(AppApi.MEDIA_DESCRIPTION, mList.get(holder.getAdapterPosition()).getMediaDescription());
                intent.putExtra(AppApi.MEDIA_POST, mList.get(holder.getAdapterPosition()).getMediaAdded());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;
        TextView imageDescription, postDate;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImage = itemView.findViewById(R.id.gallery_image);
            imageDescription = itemView.findViewById(R.id.image_description);
            postDate = itemView.findViewById(R.id.post_date);
            cardHolder = itemView.findViewById(R.id.holder);

        }
    }
}
