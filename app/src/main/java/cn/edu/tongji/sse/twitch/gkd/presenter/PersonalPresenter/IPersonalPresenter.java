package cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IPersonalPresenter {
    void showRunningData(String userID, RecyclerView running_data, Context context);
    void showRanking(String userID,RecyclerView ranking_list, Context context);
}
