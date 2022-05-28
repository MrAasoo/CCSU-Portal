package com.college.portal.clubs;


import static com.college.portal.clubs.ClubsActivity.mViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.activity.SignInActivity;
import com.college.portal.api.RetrofitClient;
import com.college.portal.clubs.adapter.JoinClubListAdapter;
import com.college.portal.clubs.model.Club;
import com.college.portal.model.StudentPref;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubFragment extends Fragment {

    protected JoinClubListAdapter mAdapter;
    protected ArrayList<Club> mList;
    private View view;
    private LinearLayout llNoClub;
    private Button btnJoinClub;
    private RecyclerView recyclerView;

    public ClubFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club, container, false);


        llNoClub = view.findViewById(R.id.no_clubs);
        btnJoinClub = view.findViewById(R.id.btn_join_club);

        btnJoinClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });


        recyclerView = view.findViewById(R.id.recycler_view_club);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mList = new ArrayList<>();
        mAdapter = new JoinClubListAdapter(view.getContext(), mList);
        recyclerView.setAdapter(mAdapter);

        SharedPrefManager instance = SharedPrefManager.getInstance(getActivity());
        if (instance.isLoggedIn()) {
            StudentPref studentPref = instance.getStudentInfo();
            getClubList(studentPref.getStdId(), "0");
        } else {
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return view;
    }

    private void getClubList(String stdId, String clubId) {

        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getCollegeClub(stdId, clubId);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response String", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Log.i("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        writeRecycler(jsonResponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "Something went wrong .....", t);
            }
        });

    }

    private void writeRecycler(String jsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    Club club = new Club();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    if (!jsonObject.getString("club_id").equals("null")) {

                        club.setClubId(Integer.valueOf(jsonObject.getString("club_id")));
                        club.setClubName(jsonObject.getString("club_name"));
                        club.setJoinDate(jsonObject.getString("join_date"));
                        club.setClubLogo(jsonObject.getString("club_logo"));

                        mList.add(club);
                    }
                }

                if (mList.size() != 0) {
                    llNoClub.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    llNoClub.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
