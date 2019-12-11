package cn.edu.tongji.sse.twitch.gkd.presenter.TrackPresenter;

import com.amap.api.track.query.entity.Point;

import java.util.List;

public interface ITrackPresenter {
    void startRun();//开始运动
    void pauseRun();//暂停运动
    void stopRun(); //停止
    void startService();//开启服务
    long getTrackId();
}
