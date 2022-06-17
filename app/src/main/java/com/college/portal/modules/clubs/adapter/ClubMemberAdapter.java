package com.college.portal.modules.clubs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.modules.clubs.model.Member;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClubMemberAdapter extends RecyclerView.Adapter<ClubMemberAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Member> mList;

    public ClubMemberAdapter(Context mContext, List<Member> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_club_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Member member = mList.get(position);

        // Values
        holder.stdName.setText(member.getStdName());
        holder.stdBranch.setText(member.getBranchName());
        holder.joinDate.setText(member.getJoinDate());

        if (member.getMemberType().equals(AppApi.CLUB_ADMIN))
            holder.isAdmin.setVisibility(View.VISIBLE);

        if (!member.getStdImage().equals("null"))
            Picasso.get().load(RetroApi.STUDENT_IMAGES + member.getStdImage()).placeholder(R.drawable.ic_app_icon).into(holder.stdImage);
        else
            holder.stdImage.setImageResource(R.drawable.ic_app_icon);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView stdImage;
        TextView isAdmin, stdName, stdBranch, joinDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stdImage = itemView.findViewById(R.id.std_image);
            isAdmin = itemView.findViewById(R.id.is_admin);
            stdName = itemView.findViewById(R.id.std_name);
            stdBranch = itemView.findViewById(R.id.std_branch);
            joinDate = itemView.findViewById(R.id.join_date);
        }
    }
}
