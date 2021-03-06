package cn.edu.tongji.sse.twitch.gkd.model.TrackModel;

import android.app.Notification;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.amap.api.track.query.entity.DriveMode;
import com.amap.api.track.query.entity.Point;
import com.amap.api.track.query.entity.Track;
import com.amap.api.track.query.model.AddTerminalRequest;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackRequest;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackRequest;
import com.amap.api.track.query.model.QueryTrackResponse;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.util.Constants;
import cn.edu.tongji.sse.twitch.gkd.util.GkdOnTrackLifecycleListener;
import cn.edu.tongji.sse.twitch.gkd.util.GkdOnTrackListener;


/*
 * 轨迹model实现
 */
public class TrackModelImpl implements ITrackModel, AMap.OnMyLocationChangeListener {
    private static final String TAG = "TrackModel1111111111111";
    private AMapTrackClient aMapTrackClient;
    private long terminalId;
    private long trackId;//轨迹id，初始值设为0
    private boolean isServiceRunning=false;
    private boolean isGatherRunning=false;
    private boolean uploadToTrack = true;

    //Attention:传入的context应当是getApplicationContext
    private Context mContext;
    private Notification mNotification;

    //构造函数，初始化一个aMapTrackClient
    public TrackModelImpl(Context mContext,Notification mNotification) {
        this.mContext=mContext;
        this.mNotification=mNotification;
        aMapTrackClient = new AMapTrackClient(mContext);
        aMapTrackClient.setInterval(5, 30);
    }

    private OnTrackLifecycleListener onTrackListener = new GkdOnTrackLifecycleListener() {
        @Override
        public void onBindServiceCallback(int status, String msg) {
            Log.w(TAG, "onBindServiceCallback, status: " + status + ", msg: " + msg);
        }

        @Override
        public void onStartTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_TRACK_SUCEE || status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK) {
                // 成功启动
                Log.w(TAG,"启动服务成功");
                isServiceRunning = true;
            } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                // 已经启动
                Log.w(TAG,"服务已经启动");
                isServiceRunning = true;
            } else {
                Log.w(TAG, "error onStartTrackCallback, status: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStopTrackCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_TRACK_SUCCE) {
                // 成功停止
                Log.w(TAG,"停止服务成功");
                isServiceRunning = false;
                isGatherRunning = false;
            } else {
                Log.w(TAG, "error onStopTrackCallback, status: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStartGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.START_GATHER_SUCEE) {
                Log.w(TAG,"定位采集开启成功");
                isGatherRunning = true;
            } else if (status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
                Log.w(TAG,"定位采集已经开启");
                isGatherRunning = true;
            } else {
                Log.w(TAG, "error onStartGatherCallback, status: " + status + ", msg: " + msg);
            }
        }

        @Override
        public void onStopGatherCallback(int status, String msg) {
            if (status == ErrorCode.TrackListen.STOP_GATHER_SUCCE) {
                Log.w(TAG,"定位采集停止成功");
                isGatherRunning = false;
            } else {
                Log.w(TAG, "error onStopGatherCallback, status: " + status + ", msg: " + msg);
            }
        }
    };

    /*
    开启轨迹服务
    */
    private void startTrack() {
        // 先根据Terminal名称查询Terminal ID，如果Terminal还不存在，就尝试创建，拿到Terminal ID后，
        // 用Terminal ID开启轨迹服务
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(Constants.SERVICE_ID, Constants.TERMINAL_NAME), new GkdOnTrackListener() {
            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()) {
                    if (queryTerminalResponse.isTerminalExist()) {
                        // 当前终端已经创建过，直接使用查询到的terminal id
                        terminalId = queryTerminalResponse.getTid();
                        if (uploadToTrack) {
                            aMapTrackClient.addTrack(new AddTrackRequest(Constants.SERVICE_ID, terminalId), new GkdOnTrackListener() {
                                @Override
                                public void onAddTrackCallback(AddTrackResponse addTrackResponse) {
                                    if (addTrackResponse.isSuccess()) {
                                        // trackId需要在启动服务后设置才能生效，因此这里不设置，而是在startGather之前设置了track id
                                        trackId = addTrackResponse.getTrid();
                                        TrackParam trackParam = new TrackParam(Constants.SERVICE_ID, terminalId);
                                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            trackParam.setNotification(mNotification);
                                        }
                                        aMapTrackClient.startTrack(trackParam, onTrackListener);
                                        Log.w(TAG,Long.toString(trackId));
                                    } else {
                                        Log.w(TAG,"网络请求失败,"+ addTrackResponse.getErrorMsg());
                                    }
                                }
                            });
                        } else {
                            // 不指定track id，上报的轨迹点是该终端的散点轨迹
                            TrackParam trackParam = new TrackParam(Constants.SERVICE_ID, terminalId);
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                trackParam.setNotification(mNotification);
                            }
                            aMapTrackClient.startTrack(trackParam, onTrackListener);
                        }
                    } else {
                        // 当前终端是新终端，还未创建过，创建该终端并使用新生成的terminal id
                        aMapTrackClient.addTerminal(new AddTerminalRequest(Constants.TERMINAL_NAME, Constants.SERVICE_ID), new GkdOnTrackListener() {
                            @Override
                            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {
                                if (addTerminalResponse.isSuccess()) {
                                    terminalId = addTerminalResponse.getTid();
                                    TrackParam trackParam = new TrackParam(Constants.SERVICE_ID, terminalId);
                                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        trackParam.setNotification(mNotification);
                                    }
                                    aMapTrackClient.startTrack(trackParam, onTrackListener);
                                } else {
                                    Log.w(TAG,"网络请求失败,"+addTerminalResponse.getErrorMsg());
                                }
                            }
                        });
                    }
                } else {
                    Log.w(TAG,queryTerminalResponse.getErrorMsg());
                }
            }
        });
    }

    //获取轨迹id,返回最新的
    @Override
    public long getTrackId(){
        return trackId;
    }

    //开启服务
    @Override
    public void startService(){
        startTrack();
    }

    //开始运动函数，是start按钮实现的函数，按下之后先启动服务
    //然后开始收集轨迹，再按下就暂停收集轨迹
    @Override
    public void startExercise() {
        //首先启动服务
        if(isServiceRunning) {
            Log.w(TAG,"服务已经启动了");
            //开始采集
            aMapTrackClient.setTrackId(trackId);
            aMapTrackClient.startGather(onTrackListener);
            Log.w(TAG,"开始采集");
        }else{
            Log.w(TAG,"服务没有启动");
        }
    }

    @Override
    public void pauseExercise(){
        //停止采集
        Log.w(TAG,"pause Exercise");
        aMapTrackClient.stopGather(onTrackListener);
    }

    //停止运动
    @Override
    public void stopExercise(){
        //如果之前没有停止收集轨迹，先停止收集。
        if (isGatherRunning) {
            aMapTrackClient.stopGather(onTrackListener);
        } else {
            Log.w(TAG,"已经停止收集轨迹了.");
        }
        //停止服务
        if(isServiceRunning) {
            aMapTrackClient.stopTrack(new TrackParam(Constants.SERVICE_ID, terminalId), onTrackListener);
            Log.w(TAG,"停止服务");
        }else{
            Log.w(TAG,"服务已经停止");
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if(location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");
            }

        } else {
            Log.e("amap", "定位失败");
        }
    }
}
