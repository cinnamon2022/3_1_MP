package com.example.meeting_app.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.meeting_app.FirebaseUtil;
import com.example.meeting_app.PostInfo;
import com.example.meeting_app.R;
import com.example.meeting_app.listener.OnPostListener;
import com.example.meeting_app.view.ReadContentsView;

public class PostActivity extends BasicActivity {
    private PostInfo postInfo;
    private FirebaseUtil firebaseUtil;
    private ReadContentsView readContentsView;
    private LinearLayout contentsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");

        contentsLayout = findViewById(R.id.contentsLayout);
        readContentsView = findViewById(R.id.readContentsView);

        firebaseUtil = new FirebaseUtil(this);
        firebaseUtil.setOnPostListener(onPostListener);
        uiUpdate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    postInfo = (PostInfo)data.getSerializableExtra("postinfo");
                    contentsLayout.removeAllViews();
                    uiUpdate();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                firebaseUtil.storageDelete(postInfo);
                return true;
            case R.id.modify:
                myStartActivity(addPostActivity.class, postInfo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(PostInfo postInfo) {
            Log.e("?????? ","?????? ??????");
        }

        @Override
        public void onModify() {
            Log.e("?????? ","?????? ??????");
        }
    };

    private void uiUpdate(){
        setToolbarTitle(postInfo.getTitle());
        readContentsView.setPostInfo(postInfo);
    }

    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("postInfo", postInfo);
        startActivityForResult(intent, 0);
    }
}