package com.college.portal.modules.clubs.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.modules.clubs.model.Member;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ClubMemberAdminAdapter extends RecyclerView.Adapter<ClubMemberAdminAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Member> mList;
    private final String mClubID;

    public ClubMemberAdminAdapter(Context mContext, List<Member> mList, String clubID) {
        this.mContext = mContext;
        this.mList = mList;
        this.mClubID = clubID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_club_member_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Member member = mList.get(position);

        // Values
        holder.stdName.setText(member.getStdName());
        holder.stdBranch.setText(member.getBranchName());
        holder.joinDate.setText(member.getJoinDate());

        if (!member.getStdImage().equals("null"))
            Picasso.get().load(RetroApi.STUDENT_IMAGES + member.getStdImage()).placeholder(R.drawable.ic_app_icon).into(holder.stdImage);
        else
            holder.stdImage.setImageResource(R.drawable.ic_app_icon);

        // HIDE/SHOW Admin Controls
        if (member.getMemberType().equals(AppApi.CLUB_MEMBER)) {
            switch (member.getMemberStatus()) {
                case AppApi.MEMBER_YES:
                    holder.isMemberLL.setVisibility(View.VISIBLE);
                    break;
                case AppApi.MEMBER_NO:
                    holder.requestLL.setVisibility(View.VISIBLE);
                    break;
                case AppApi.MEMBER_BLOCKED:
                    holder.isBlockedLL.setVisibility(View.VISIBLE);
                    break;
            }
        } else
            holder.isAdmin.setVisibility(View.VISIBLE);


        // Manage members
        // is member
        // removing member
        holder.removeMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isMemberLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.REMOVE_MEMBER, member.getSrNo(), null, holder.isMemberLL);
            }
        });

        //block member
        holder.blockMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isMemberLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.BLOCK_MEMBER, member.getSrNo(), holder.isBlockedLL, holder.isMemberLL);
            }
        });

        //is blocked
        //removing member
        holder.removeMember2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isBlockedLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.REMOVE_MEMBER, member.getSrNo(), null, holder.isBlockedLL);
            }
        });

        //unblock member
        holder.unblockMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isBlockedLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.UNBLOCK_MEMBER, member.getSrNo(), holder.isMemberLL, holder.isBlockedLL);


            }
        });

        // request
        // accept req
        holder.acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.requestLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.ACCEPT_MEMBER, member.getSrNo(), holder.isMemberLL, holder.requestLL);

            }
        });

        // cancel req
        holder.cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.requestLL.setVisibility(View.GONE);
                clubMemberRequest(member.getStdId(), mClubID, AppApi.CANCEL_REQ, member.getSrNo(), null, holder.requestLL);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clubMemberRequest(String stdId, String clubId, int req, String srNo, View ifView, View elseView) {

        Call<JsonObject> call = RetrofitClient.getInstance().getRetroApi().getCollegeClubMember(stdId, clubId, req, srNo);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonResponse = response.body().toString();
                        //Log.e("ManageClubMembers", "onResponse: " + jsonResponse);
                        try {
                            JSONObject obj = new JSONObject(jsonResponse);
                            if (obj.optBoolean("status")) {
                                switch (Integer.valueOf(obj.optString("result"))) {
                                    case AppApi.REMOVE_MEMBER:
                                        Toast.makeText(mContext, "Member removed!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case AppApi.BLOCK_MEMBER:
                                        Toast.makeText(mContext, "Member blocked!", Toast.LENGTH_SHORT).show();
                                        ifView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.UNBLOCK_MEMBER:
                                        Toast.makeText(mContext, "Member Unblocked!", Toast.LENGTH_SHORT).show();
                                        ifView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.ACCEPT_MEMBER:
                                        Toast.makeText(mContext, "Member added!", Toast.LENGTH_SHORT).show();
                                        ifView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.CANCEL_REQ:
                                        Toast.makeText(mContext, "Member request canceled!", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } else {
                                switch (Integer.valueOf(obj.optString("result"))) {
                                    case AppApi.REMOVE_MEMBER:
                                        Toast.makeText(mContext, "Removing member failed!", Toast.LENGTH_SHORT).show();
                                        elseView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.BLOCK_MEMBER:
                                        Toast.makeText(mContext, "Blocking failed!", Toast.LENGTH_SHORT).show();
                                        elseView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.UNBLOCK_MEMBER:
                                        Toast.makeText(mContext, "Unblocking failed!", Toast.LENGTH_SHORT).show();
                                        elseView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.ACCEPT_MEMBER:
                                        Toast.makeText(mContext, "Accepting request failed!", Toast.LENGTH_SHORT).show();
                                        elseView.setVisibility(View.VISIBLE);
                                        break;
                                    case AppApi.CANCEL_REQ:
                                        Toast.makeText(mContext, "Cancelling request failed!", Toast.LENGTH_SHORT).show();
                                        elseView.setVisibility(View.VISIBLE);
                                        break;
                                }

                            }
                        } catch (JSONException e) {
                            Log.e("ClubPageActivity", "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "ManageClubMembers ", t);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView stdImage;
        TextView stdName, isAdmin, stdBranch, joinDate;
        LinearLayout isMemberLL, requestLL, isBlockedLL;
        Button removeMember, blockMember, cancelRequest, acceptRequest, removeMember2, unblockMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stdImage = itemView.findViewById(R.id.std_image);
            stdName = itemView.findViewById(R.id.std_name);
            stdBranch = itemView.findViewById(R.id.std_branch);
            joinDate = itemView.findViewById(R.id.join_date);
            isAdmin = itemView.findViewById(R.id.is_admin);

            //LL
            isMemberLL = itemView.findViewById(R.id.is_member);
            requestLL = itemView.findViewById(R.id.request);
            isBlockedLL = itemView.findViewById(R.id.is_blocked);

            //Buttons
            removeMember = itemView.findViewById(R.id.remove_member);
            blockMember = itemView.findViewById(R.id.block_member);
            cancelRequest = itemView.findViewById(R.id.cancel_request);
            acceptRequest = itemView.findViewById(R.id.accept_request);
            removeMember2 = itemView.findViewById(R.id.remove_member_2);
            unblockMember = itemView.findViewById(R.id.unblock_member);

        }
    }

}
