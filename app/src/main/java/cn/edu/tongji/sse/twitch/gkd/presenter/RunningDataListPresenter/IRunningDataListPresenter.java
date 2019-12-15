package cn.edu.tongji.sse.twitch.gkd.presenter.RunningDataListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IRunningDataListPresenter {
    void showRunningData(String userID, RecyclerView running_data, Context context);
}
