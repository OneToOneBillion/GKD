package cn.edu.tongji.sse.twitch.gkd.model.SocialModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface ISocialModel {
    void showPostList(String userID, RecyclerView recyclerView, Context context,OnShowPostListener onShowPostListener);
    void addPostLikes(String userID,String time,String likesname,OnShowPostListener onShowPostListener);
    void deletePostLikes(String userID,String time,String likesname,OnShowPostListener onShowPostListener);

    public interface OnShowPostListener{
        void showPostSuccess();
        void showPostFailed();
    }
}
