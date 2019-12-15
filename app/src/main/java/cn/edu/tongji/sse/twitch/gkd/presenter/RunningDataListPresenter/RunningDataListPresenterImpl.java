package cn.edu.tongji.sse.twitch.gkd.presenter.RunningDataListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.IPersonalModel;
import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.PersonalModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.RunningDataListModel.IRunningDataListModel;
import cn.edu.tongji.sse.twitch.gkd.model.RunningDataListModel.RunningDataListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;
import cn.edu.tongji.sse.twitch.gkd.view.RunningDataListView.IRunningDataListView;

public class RunningDataListPresenterImpl implements IRunningDataListPresenter, IRunningDataListModel.OnShowRunningDataListener {
    private IRunningDataListModel iRunningDataListModel;
    private IRunningDataListView iRunningDataListView;
    public RunningDataListPresenterImpl(IRunningDataListView IRunningDataListView){
        iRunningDataListView=IRunningDataListView;
        iRunningDataListModel=new RunningDataListModelImpl(this);
    }

    public void showRunningData(String userID, RecyclerView running_data, Context context){
        iRunningDataListModel.ShowRunningDataList(userID,running_data,context,this);
    }

    public void ShowRunningDataSuccess(){

    }
    public void ShowRunningDataFailed(){

    }
}
