package cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface IFollowedListPresenter {
    void showFollowed(String userID, RecyclerView followed_list, Context context);
}
