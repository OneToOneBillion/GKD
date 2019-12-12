package cn.edu.tongji.sse.twitch.gkd.presenter.TrackSearchPresenter;

import com.amap.api.track.query.entity.Point;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.TrackModel.ITrackModel;
import cn.edu.tongji.sse.twitch.gkd.model.TrackModel.TrackModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.IRunningView;

public class TrackSearchPresenterImpl implements ITrackSearchPresenter{
    private ITrackModel mITrackModel;
    private IRunningView mIRunningView;


    public TrackSearchPresenterImpl(IRunningView IRunningView){
        this.mIRunningView=IRunningView;
        mITrackModel=new TrackModelImpl(mIRunningView.getMyContext(),mIRunningView.getMyNotification());
    }


}
