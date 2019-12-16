package cn.edu.tongji.sse.twitch.gkd.model.FollowListModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.model.RankingListModel.IRankingListModel;

public interface IFollowListModel {
    void showFollowList(String userID, RecyclerView follow_list_recyclerView, Context context, IFollowListModel.OnShowFollowListener onShowFollowListener);

    public interface OnShowFollowListener{
        void showFollowSuccess();
        void showFollowFailed();
    }
}
