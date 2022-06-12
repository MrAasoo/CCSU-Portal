package com.college.portal.modules.studentzone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.modules.studentzone.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Subject> mList;

    public SubjectAdapter(Context mContext, List<Subject> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_subject, parent, false);
        return new SubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.viewHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        Subject subject = mList.get(position);

        // Values
        holder.subjectCode.setText(subject.getSubjectCode());
        holder.subjectName.setText(subject.getSubjectName());
        holder.facultyName.setText(subject.getFacultyName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectCode, subjectName, facultyName;
        LinearLayout viewHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolder = itemView.findViewById(R.id.view_holder);
            subjectCode = itemView.findViewById(R.id.subject_code);
            subjectName = itemView.findViewById(R.id.subject_name);
            facultyName = itemView.findViewById(R.id.faculty_name);

        }
    }
}
