package com.example.meeting_app.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.meeting_app.R;
import com.example.meeting_app.fragment.HomeFragment;
import com.example.meeting_app.fragment.SmallFragment;
import com.example.meeting_app.fragment.UserInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SmallActivity extends BasicActivity {
    private static final String TAG = "SmallActivity";

    @Override // Different between back pressed code.
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);
        setToolbarTitle(getResources().getString(R.string.app_name));

        init();
    }

    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                init();
                break;
        }
    }
    private void init(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            startMyActivity(SignupActivity.class);
        } else {
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                startMyActivity(MemberInfoActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

            SmallFragment smallFragment = new SmallFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, smallFragment)
                    .commit();

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            SmallFragment smallFragment = new SmallFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, smallFragment)
                                    .commit();
                            return true;
                        case R.id.myInfo:
                            UserInfoFragment userInfoFragment = new UserInfoFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, userInfoFragment)
                                    .commit();
                            return true;
                    }
                    return false;
                }
            });
        }
    }
    private void startMyActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }
}