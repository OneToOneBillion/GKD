package cn.edu.tongji.sse.twitch.gkd.model.RankingListModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.ISocialModel;

public interface IRankingListModel {
    void showRankingList(String userID, RecyclerView ranking_list_recyclerView, Context context, IRankingListModel.OnShowRankingListener onShowRankingListener);

    public interface OnShowRankingListener{
        void showRankingSuccess();
        void showRankingFailed();
    }
}
