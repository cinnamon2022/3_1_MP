package com.example.meeting_app.listener;

import com.example.meeting_app.PostInfo;

public interface OnPostListener {

    void onDelete(PostInfo postInfo);
    void onModify();
}