package cn.edu.tongji.sse.twitch.gkd.model.RunningDataListModel;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.IPersonalModel;

public interface IRunningDataListModel {
    void ShowRunningDataList(String userID, RecyclerView running_data, Context context,
                             IRunningDataListModel.OnShowRunningDataListener onShowRunningDataListener);

    public interface OnShowRunningDataListener{
        void ShowRunningDataSuccess();
        void ShowRunningDataFailed();
    }
}
