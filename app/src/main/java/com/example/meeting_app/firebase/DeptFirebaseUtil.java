package com.example.meeting_app.firebase;

import static com.example.meeting_app.Util.isStorageUrl;
import static com.example.meeting_app.Util.startToast;
import static com.example.meeting_app.Util.storageUrlToName;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.meeting_app.PostInfo;
import com.example.meeting_app.listener.OnPostListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeptFirebaseUtil {
    private Activity activity;
    private OnPostListener onPostListener;
    private int successCount;

    public DeptFirebaseUtil(Activity activity) {
        this.activity = activity;
    }

    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    public void storageDelete(final PostInfo postInfo){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        final String id = postInfo.getId();
        ArrayList<String> contentsList = postInfo.getContents();
        for (int i = 0; i < contentsList.size(); i++) {
            String contents = contentsList.get(i);
            if (isStorageUrl(contents)) {
                successCount++;
                StorageReference desertRef = storageRef.child("dept_posts/" + id + "/" + storageUrlToName(contents));
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        successCount--;
                        storeDelete(id, postInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        startToast(activity, "Error");
                    }
                });
            }
        }
        storeDelete(id, postInfo);
    }

    private void storeDelete(final String id, final PostInfo postInfo) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        if (successCount == 0) {
            firebaseFirestore.collection("dept_posts").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startToast(activity, "게시글을 삭제하였습니다.");
                            onPostListener.onDelete(postInfo);
                            //postsUpdate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast(activity, "게시글을 삭제하지 못하였습니다.");
                        }
                    });
        }
    }
}
