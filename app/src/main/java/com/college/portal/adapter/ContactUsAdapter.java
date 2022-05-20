package com.college.portal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.model.ContactUs;

import java.util.List;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolder> {

    private Context mContext;
    private List<ContactUs> mList;

    public ContactUsAdapter(Context mContext, List<ContactUs> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_contact_us, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));


        holder.contactName.setText(mList.get(position).getContactName());
        if (!mList.get(position).getContactNumber().isEmpty()) {
            holder.cn.setVisibility(View.VISIBLE);
            holder.contactNumber.setText(mList.get(position).getContactNumber());
        }

        if (!mList.get(position).getContactDesignation().isEmpty()) {
            holder.contactDesignation.setVisibility(View.VISIBLE);
            holder.contactDesignation.setText(mList.get(position).getContactDesignation());
        }

        if (!mList.get(position).getContactEmail().isEmpty()) {
            holder.ce.setVisibility(View.VISIBLE);
            holder.contactEmail.setText(mList.get(position).getContactEmail());
        }

        if (!mList.get(position).getContactLink().isEmpty()) {
            holder.web.setVisibility(View.VISIBLE);
            holder.contactWeb.setText(mList.get(position).getContactLink());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contactName, contactNumber, contactEmail, contactWeb, contactDesignation;
        LinearLayout cn, ce, web;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHolder = itemView.findViewById(R.id.holder);
            contactName = itemView.findViewById(R.id.contact_name);
            contactDesignation = itemView.findViewById(R.id.contact_desi);
            contactNumber = itemView.findViewById(R.id.contact_number);
            contactEmail = itemView.findViewById(R.id.contact_email);
            contactWeb = itemView.findViewById(R.id.contact_web);
            cn = itemView.findViewById(R.id.contact_no_ll);
            ce = itemView.findViewById(R.id.email_ll);
            web = itemView.findViewById(R.id.website_ll);
        }
    }
}
