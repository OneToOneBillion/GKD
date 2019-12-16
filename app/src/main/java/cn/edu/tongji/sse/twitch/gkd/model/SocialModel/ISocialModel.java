package cn.edu.tongji.sse.twitch.gkd.model.SocialModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface ISocialModel {
    void showPostList(String userID, RecyclerView recyclerView, Context context,OnShowPostListener onShowPostListener);

    public interface OnShowPostListener{
        void showPostSuccess();
        void showPostFailed();
    }
}
