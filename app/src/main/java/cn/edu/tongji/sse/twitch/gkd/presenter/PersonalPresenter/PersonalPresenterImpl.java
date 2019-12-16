package cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.IPersonalModel;
import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.PersonalModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;

public class PersonalPresenterImpl implements IPersonalPresenter, IPersonalModel.OnShowRankingListener,IPersonalModel.OnShowRunningDataListener {
    private IPersonalView iPersonalView;
    private IPersonalModel iPersonalModel;

    public PersonalPresenterImpl(IPersonalView IPersonalView){
        iPersonalView=IPersonalView;
        iPersonalModel=new PersonalModelImpl(this);
    }

    public void showRunningData(String userID, RecyclerView running_data, Context context){
        iPersonalModel.ShowRunningDataList(userID,running_data,context,this);
    }
    public void showRanking(String userID,RecyclerView ranking_list, Context context){
        iPersonalModel.ShowRankingList(userID,ranking_list,context,this);
    }
    public void ShowRunningDataSuccess(){

    }
    public void ShowRunningDataFailed(){

    }
    public void ShowRankingSuccess(){

    }
    public void ShowRankingFailed(){

    }
}
