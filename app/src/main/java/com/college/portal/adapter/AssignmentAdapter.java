package com.college.portal.adapter;

import android.annotation.SuppressLint;
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
import com.college.portal.model.Assignment;
import com.college.portal.model.ContactUs;

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

        holder.assiTitle.setText(mList.get(position).getAssiTitle());
        holder.assiMessage.setText(mList.get(position).getAssiType());
        holder.assiDate.setText("Submission Date : " + mList.get(position).getAssiDate());

        holder.assiClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Assignment ID : " + mList.get(position).getAssiId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView assiTitle, assiMessage, assiDate, assiClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assiTitle = itemView.findViewById(R.id.assi_title);
            assiMessage = itemView.findViewById(R.id.assi_message);
            assiDate = itemView.findViewById(R.id.assi_date);
            assiClick = itemView.findViewById(R.id.assi_click);

        }
    }
}
