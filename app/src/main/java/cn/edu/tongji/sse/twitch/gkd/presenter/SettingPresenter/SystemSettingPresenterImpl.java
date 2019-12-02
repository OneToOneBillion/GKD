package cn.edu.tongji.sse.twitch.gkd.presenter.SettingPresenter;

import java.lang.ref.WeakReference;

import cn.edu.tongji.sse.twitch.gkd.view.SettingView.SystemSettingActivity;

public class SystemSettingPresenterImpl<V extends SystemSettingActivity> implements ISystemSettingPresenter<V> {

    private WeakReference<V> mSettingViewRef;
    public V mSettingView;

    @Override
    public void attachView(V view) {
        mSettingViewRef = new WeakReference<V>(view);
        mSettingView = mSettingViewRef.get();
    }

    public void detachView() {
        mSettingViewRef.clear();
        mSettingView = null;
    }

    @Override
    public void saveSetting() {
        ;
    }
}
