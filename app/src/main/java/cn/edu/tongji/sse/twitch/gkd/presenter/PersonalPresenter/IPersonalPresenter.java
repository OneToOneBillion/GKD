package cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IPersonalPresenter {
    void showRunningData(String userID, List<String> show_running_data_list, RecyclerView running_data, Context context);
    void showRanking(String userID,List<String> show_ranking_list,RecyclerView ranking_list, Context context);
}
