package com.example.meeting_app.adpat;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meeting_app.PostInfo;
import com.example.meeting_app.R;
import com.example.meeting_app.activity.PostActivity;
import com.example.meeting_app.activity.addDeptPostActivity;
import com.example.meeting_app.activity.addPostActivity;
import com.example.meeting_app.firebase.DeptFirebaseUtil;
import com.example.meeting_app.listener.OnPostListener;
import com.example.meeting_app.view.ReadContentsView;

import java.util.ArrayList;

public class DeptAdapt extends RecyclerView.Adapter<DeptAdapt.MainViewHolder>{

    private ArrayList<PostInfo> mDataset;
    private Activity activity;
    private DeptFirebaseUtil deptFirebaseUtil;
    private final int MORE_INDEX = 2;

    static class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        MainViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public DeptAdapt(Activity activity, ArrayList<PostInfo> myDataset) {
        this.mDataset = myDataset;
        this.activity = activity;

        deptFirebaseUtil = new DeptFirebaseUtil(activity);
    }

    public void setOnPostListener(OnPostListener onPostListener){
        deptFirebaseUtil.setOnPostListener(onPostListener);
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public DeptAdapt.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final DeptAdapt.MainViewHolder mainViewHolder = new DeptAdapt.MainViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PostActivity.class);
                intent.putExtra("postInfo", mDataset.get(mainViewHolder.getAdapterPosition()));
                activity.startActivity(intent);
            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition());
            }
        });

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeptAdapt.MainViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);

        PostInfo postInfo = mDataset.get(position);
        titleTextView.setText(postInfo.getTitle());

        ReadContentsView readContentsVIew = cardView.findViewById(R.id.readContentsView);
        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);

        if (contentsLayout.getTag() == null || !contentsLayout.getTag().equals(postInfo)) {
            contentsLayout.setTag(postInfo);
            contentsLayout.removeAllViews();

            readContentsVIew.setMoreIndex(MORE_INDEX);
            readContentsVIew.setPostInfo(postInfo);

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:
                        myStartActivity(addDeptPostActivity.class, mDataset.get(position));
                        return true;
                    case R.id.delete:
                        deptFirebaseUtil.storageDelete(mDataset.get(position));
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }

    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("postInfo", postInfo);
        activity.startActivity(intent);
    }
}
