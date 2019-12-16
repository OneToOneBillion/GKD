package cn.edu.tongji.sse.twitch.gkd.model.PersonalModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IPersonalModel {
    void ShowRunningDataList(String userID, RecyclerView running_data, Context context, OnShowRunningDataListener onShowRunningDataListener);
    void ShowRankingList(String userID,RecyclerView ranking_list, Context context,OnShowRankingListener onShowRankingListener);

    public interface OnShowRunningDataListener{
        void ShowRunningDataSuccess();
        void ShowRunningDataFailed();
    }

    public interface OnShowRankingListener{
        void ShowRankingSuccess();
        void ShowRankingFailed();
    }
}
