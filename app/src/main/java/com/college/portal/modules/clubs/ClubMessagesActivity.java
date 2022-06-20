package com.college.portal.modules.clubs;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.clubs.adapter.ClubMessageAdapter;
import com.college.portal.modules.clubs.model.ClubChats;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ClubMessagesActivity extends AppCompat {

    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://college-portal-353009-default-rtdb.firebaseio.com/");
    //user data
    SharedPrefManager prefManager = new SharedPrefManager(ClubMessagesActivity.this);
    //Views
    private EditText textMessage;
    private ImageButton btnSend;
    private RecyclerView recyclerView;
    private ClubMessageAdapter mAdapter;
    private List<ClubChats> mList;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_message);

        // Firebase user
        mAuth = FirebaseAuth.getInstance();

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(AppApi.CLUB_NAME));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);


        // init views
        textMessage = findViewById(R.id.text_message);
        btnSend = findViewById(R.id.send_msg);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.setClickable(false);
                if (textMessage.getText().toString().trim().isEmpty()) {
                    textMessage.requestFocus();
                    textMessage.setError("Can't send empty message.");
                    btnSend.setClickable(true);
                } else {
                    if (prefManager.isLoggedIn()) {
                        sendMessage(textMessage.getText().toString().trim(),
                                prefManager.getStudentInfo().getStdId(),
                                prefManager.getStudentInfo().getStdName(),
                                getIntent().getStringExtra(AppApi.CLUB_KEY));
                    } else {
                        Toast.makeText(ClubMessagesActivity.this, "Can't send message.", Toast.LENGTH_SHORT).show();
                        btnSend.setClickable(true);
                    }
                }
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new ClubMessageAdapter(ClubMessagesActivity.this, mList);
        recyclerView.setAdapter(mAdapter);

        getClassroomChatData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
        updateFirebaseUser(mUser);
    }

    private void updateFirebaseUser(FirebaseUser user) {

        StudentPref prefManager = SharedPrefManager.getInstance(ClubMessagesActivity.this).getStudentInfo();

        if (user == null) {
            mAuth.signInWithEmailAndPassword(prefManager.getStdId() + "@stportal.com", prefManager.getStdPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ClubMessagesActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                mUser = mAuth.getCurrentUser();
                            } else {
                                Toast.makeText(ClubMessagesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void getClassroomChatData() {

        reference.child("club_chats").child(getIntent().getStringExtra(AppApi.CLUB_KEY)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {

                    ClubChats chat = data.getValue(ClubChats.class);

                    mList.add(chat);

                }
                if (mList.size() > 1)
                    recyclerView.smoothScrollToPosition(mList.size() - 1);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String message, String stdId, String stdName, String roomKey) {

        // Date Time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String time = timeFormat.format(Calendar.getInstance().getTime());

        //Log.d("ClassroomMessages", "sendMessage: "+ message + " " + stdId + " " + stdName + " " + roomKey + " " + date + " " + time);

        // push message in database
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("std_id", stdId);
        map.put("std_name", stdName);
        map.put("date", date);
        map.put("time", time);

        reference.child("club_chats").child(roomKey).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    textMessage.setText("");
                    btnSend.setClickable(true);
                } else {
                    Toast.makeText(ClubMessagesActivity.this, "Message not sent", Toast.LENGTH_SHORT).show();
                    btnSend.setClickable(true);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mInternetBroadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());

    }


    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}