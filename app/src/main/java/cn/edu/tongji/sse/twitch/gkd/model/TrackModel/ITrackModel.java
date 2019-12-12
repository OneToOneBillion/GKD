package cn.edu.tongji.sse.twitch.gkd.model.TrackModel;

import com.amap.api.track.query.entity.Point;

import java.util.List;

public interface ITrackModel {
    void startExercise();//开始运动
    void stopExercise();//停止运动
    void pauseExercise();
    void startService();//开启服务
    long getTrackId();
}
