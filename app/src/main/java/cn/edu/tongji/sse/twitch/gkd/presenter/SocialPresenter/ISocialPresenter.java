package cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.ISocialModel;

public interface ISocialPresenter {
    void showPost(String userID, RecyclerView recyclerView, Context context);
}
