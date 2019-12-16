package cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.RunningModel.IRunningModel;
import cn.edu.tongji.sse.twitch.gkd.model.RunningModel.RunningModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.IRunningView;

public class RunningPresenterImpl implements IRunningPresenter,IRunningModel.OnAddlistener{
    private IRunningModel iRunningModel;
    private IRunningView iRunningView;

    public RunningPresenterImpl(IRunningView IRunningView){
        iRunningView=IRunningView;
        iRunningModel=new RunningModelImpl(this);
    }

    public void addNewRunData(String userID,long run_time,String run_distance){
        iRunningModel.addRunData(userID,run_time,run_distance,this);
    }

    public String findUsername(String userID){
        return iRunningModel.findUsernamebyID(userID,this);
    }

    public void AddSuccess(){

    }
    public void AddFailed(){

    }
}
