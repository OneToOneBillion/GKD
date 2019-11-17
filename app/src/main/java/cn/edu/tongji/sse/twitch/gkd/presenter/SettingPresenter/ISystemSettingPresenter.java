package cn.edu.tongji.sse.twitch.gkd.presenter.SettingPresenter;

import cn.edu.tongji.sse.twitch.gkd.view.SettingView.SystemSettingActivity;

public interface ISystemSettingPresenter<V extends SystemSettingActivity> {
    //绑定SettingView
    void attachView(V view);
    //解除与SettingView的绑定
    void detachView();

    void saveSetting();

}
