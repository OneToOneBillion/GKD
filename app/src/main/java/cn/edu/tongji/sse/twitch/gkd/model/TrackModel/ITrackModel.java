package cn.edu.tongji.sse.twitch.gkd.model.TrackModel;

import com.amap.api.track.query.entity.Point;

import java.util.List;

public interface ITrackModel {
    void startExerise();//开始运动
    void stopExerise();//停止运动
    double getIDistance();
    List<Point> getPoints();

}
