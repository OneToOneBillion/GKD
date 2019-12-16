package cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface IFollowListPresenter {
    void showFollow(String userID, RecyclerView follow_list, Context context);
}
