package com.example.meeting_app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.meeting_app.R;

public class ContentsitemView extends LinearLayout{    private ImageView imageView;
    private EditText editText;

    public ContentsitemView(Context context) {
        super(context);
        initView();
    }

    public ContentsitemView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView(){
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(layoutInflater.inflate(R.layout.view_content_image, this, false));
        addView(layoutInflater.inflate(R.layout.view_content_edit, this, false));

        imageView = findViewById(R.id.contentsImageView);
        editText = findViewById(R.id.contentsEditText);
    }

    public void setImage(String path){
        Glide.with(this).load(path).override(1000).into(imageView);
    }

    public void setText(String text){
        editText.setText(text);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        imageView.setOnClickListener(onClickListener);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener){
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }
}
