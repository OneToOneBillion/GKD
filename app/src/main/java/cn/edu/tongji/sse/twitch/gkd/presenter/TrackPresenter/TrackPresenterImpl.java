package cn.edu.tongji.sse.twitch.gkd.presenter.TrackPresenter;

import com.amap.api.track.query.entity.Point;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.TrackModel.ITrackModel;
import cn.edu.tongji.sse.twitch.gkd.model.TrackModel.TrackModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.IRunningView;

public class TrackPresenterImpl implements ITrackPresenter {
    private ITrackModel mITrackModel;
    private IRunningView mIRunningView;

    public TrackPresenterImpl(IRunningView IRunningView){
        this.mIRunningView=IRunningView;
        mITrackModel=new TrackModelImpl(mIRunningView.getMyContext(),mIRunningView.getMyNotification());
    }

    @Override
    public void startService(){
        mITrackModel.startService();
    }

    //开始运动
    @Override
    public void startRun(){
        mITrackModel.startExercise();
    }

    //暂停运动
    @Override
    public void pauseRun(){
        mITrackModel.pauseExercise();
    }

    //停止
    @Override
    public void stopRun(){
        mITrackModel.stopExercise();
    }

    @Override
    public long getTrackId(){ return mITrackModel.getTrackId(); }
}
