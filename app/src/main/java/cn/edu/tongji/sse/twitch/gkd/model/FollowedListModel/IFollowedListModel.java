package cn.edu.tongji.sse.twitch.gkd.model.FollowedListModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.model.FollowListModel.IFollowListModel;

public interface IFollowedListModel {
    void showFollowedList(String userID, RecyclerView followed_list_recyclerView, Context context, IFollowedListModel.OnShowFollowedListener onShowFollowedListener);

    public interface OnShowFollowedListener{
        void showFollowedSuccess();
        void showFollowedFailed();
    }
}
