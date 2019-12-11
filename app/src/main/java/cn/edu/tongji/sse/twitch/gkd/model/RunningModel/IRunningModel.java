package cn.edu.tongji.sse.twitch.gkd.model.RunningModel;

public interface IRunningModel {
    void addRunData(String userID,long run_time,OnAddlistener onAddlistener);
    //暂时绑定此接口，之后会做修改
    String findUsernamebyID(String userID,OnAddlistener onAddlistener);

    public interface OnAddlistener{
        void AddSuccess();
        void AddFailed();
    }
}
