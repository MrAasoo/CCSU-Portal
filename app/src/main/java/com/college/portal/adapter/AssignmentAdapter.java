package com.college.portal.adapter;

import static com.college.portal.api.AppApi.ASSIGNMENT_ID;

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
import com.college.portal.activity.AssignmentActivity;
import com.college.portal.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private Context mContext;
    private List<Assignment> mList;

    public AssignmentAdapter(Context mContext, List<Assignment> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_assignment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Card Animation
        holder.cardHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        // Values
        holder.assiTitle.setText(mList.get(position).getAssiTitle());
        holder.assiMessage.setText(mList.get(position).getAssiDetails());
        holder.assiDate.setText(String.format(mContext.getString(R.string.assignment_date), mList.get(position).getAssiDate()));

        holder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Assignment ID : " + mList.get(holder.getAdapterPosition()).getAssiId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AssignmentActivity.class);
                intent.putExtra(ASSIGNMENT_ID, mList.get(holder.getAdapterPosition()).getAssiId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView assiTitle, assiMessage, assiDate;
        CardView cardHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assiTitle = itemView.findViewById(R.id.assi_title);
            assiMessage = itemView.findViewById(R.id.assi_details);
            assiDate = itemView.findViewById(R.id.assi_date);
            cardHolder = itemView.findViewById(R.id.holder);

        }
    }
}
