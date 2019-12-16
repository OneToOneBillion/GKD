package cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter;

public interface IRunningPresenter {
    void addNewRunData(String userID,long run_time);
    String findUsername(String userID);
}
