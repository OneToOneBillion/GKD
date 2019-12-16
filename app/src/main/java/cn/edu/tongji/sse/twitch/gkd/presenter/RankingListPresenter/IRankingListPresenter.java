package cn.edu.tongji.sse.twitch.gkd.presenter.RankingListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IRankingListPresenter {
    void showRanking(String userID, RecyclerView ranking_list, Context context);
}
