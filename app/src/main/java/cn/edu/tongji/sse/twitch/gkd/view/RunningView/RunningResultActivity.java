package cn.edu.tongji.sse.twitch.gkd.view.RunningView;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.query.entity.DriveMode;
import com.amap.api.track.query.entity.Point;
import com.amap.api.track.query.entity.Track;
import com.amap.api.track.query.model.DistanceRequest;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackRequest;
import com.amap.api.track.query.model.QueryTrackResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.IRunningPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.RunningPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.TrackSearchPresenter.TrackSearchPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.util.Constants;
import cn.edu.tongji.sse.twitch.gkd.util.GkdOnTrackListener;

public class RunningResultActivity extends AppCompatActivity implements IRunningView, AMap.OnMapScreenShotListener {
    private TextView tSportReport;
    private TextureMapView textureMapView;
    private List<Marker> endMarkers = new LinkedList<>();
    private List<Polyline> polylines = new LinkedList<>();
    private AMapTrackClient aMapTrackClient;
    private Button btn_punchin;
    long trackId;
    private ImageButton myPost, btn_revert;

    private IRunningPresenter iRunningPresenter;
    private TrackSearchPresenterImpl mTrackSearchPresenter;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_result_view);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        tSportReport=findViewById(R.id.sportReportText);
        aMapTrackClient = new AMapTrackClient(getApplicationContext());
        mTrackSearchPresenter=new TrackSearchPresenterImpl(this);

        iRunningPresenter=new RunningPresenterImpl(this);
        TextView distanceText=(TextView)findViewById(R.id.distance);
        textureMapView = findViewById(R.id.showMap);
        textureMapView.onCreate(savedInstanceState);
        Intent intent=getIntent();
        trackId=intent.getLongExtra("trackId",0);
        btn_punchin=findViewById(R.id.btn_punchin);
        btn_revert=findViewById(R.id.revertBtn);

        clearTracksOnMap();
        Log.w("ResultActivityTrackId", Long.toString(trackId));
        clearTracksOnMap();
        //getALLDistance(distanceText);
        draw(distanceText);
        myPost=findViewById(R.id.myPost);
        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMapScreenShot(textureMapView);
            }
        });

        //打卡按钮
        btn_punchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRunningPresenter.addNewRunData(getUserID(),getRunTime(),distanceText.getText().toString());
                Intent intent = new Intent(RunningResultActivity.this, RunningActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //返回按钮
        btn_revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningResultActivity.this,RunningActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //语言设置
        if(sysSettingSp.getString("language","").equals("English")){
            tSportReport.setText(R.string.sport_report_en);
            btn_punchin.setText(R.string.clock_in_en);
        }
        else{
            tSportReport.setText(R.string.sport_report_cn);
            btn_punchin.setText(R.string.clock_in_cn);
        }
    }

    public long getRunTime(){
        Intent intent=getIntent();
        return intent.getLongExtra("run_time",0);
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }


    @Override
    public Context getMyContext(){
        return this.getApplicationContext();
    }

    @Override
    public Notification getMyNotification(){
        return createNotification();
    }

    /**
     * 对地图进行截屏
     */
    public void getMapScreenShot(View v) {
        textureMapView.getMap().getMapScreenShot(this);
    }

    /**
     * 截屏时回调的方法。
     *
     * @param bitmap 调用截屏接口返回的截屏对象。
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap) {

    }

    /**
     * 在8.0以上手机，如果app切到后台，系统会限制定位相关接口调用频率
     * 可以在启动轨迹上报服务时提供一个通知，这样Service启动时会使用该通知成为前台Service，可以避免此限制
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification createNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID_SERVICE_RUNNING", "app service", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(), "CHANNEL_ID_SERVICE_RUNNING");
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        Intent nfIntent = new Intent(RunningResultActivity.this, RunningResultActivity.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(PendingIntent.getActivity(RunningResultActivity.this, 0, nfIntent, 0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("猎鹰sdk运行中")
                .setContentText("猎鹰sdk运行中");
        Notification notification = builder.build();
        return notification;
    }

    private void draw(TextView textView){
        // 先查询terminal id，然后用terminal id查询轨迹
        // 查询符合条件的所有轨迹，并分别绘制
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(Constants.SERVICE_ID, Constants.TERMINAL_NAME), new GkdOnTrackListener() {
            @Override
            public void onQueryTerminalCallback(final QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()) {
                    if (queryTerminalResponse.isTerminalExist()) {
                        long tid = queryTerminalResponse.getTid();
                        // 搜索最近12小时以内上报的属于某个轨迹的轨迹点信息，散点上报不会包含在该查询结果中
                        QueryTrackRequest queryTrackRequest = new QueryTrackRequest(
                                Constants.SERVICE_ID,
                                tid,
                                trackId,     // 轨迹id，不指定，查询所有轨迹，注意分页仅在查询特定轨迹id时生效，查询所有轨迹时无法对轨迹点进行分页
                                System.currentTimeMillis() - 12 * 60 * 60 * 1000,
                                System.currentTimeMillis(),
                                0,      // 不启用去噪
                                0,   // 绑路
                                0,      // 不进行精度过滤
                                DriveMode.DRIVING,  // 当前仅支持驾车模式
                                0,     // 距离补偿
                                5000,   // 距离补偿，只有超过5km的点才启用距离补偿
                                1,  // 结果应该包含轨迹点信息
                                1,  // 返回第1页数据，但由于未指定轨迹，分页将失效
                                100    // 一页不超过100条
                        );
                        aMapTrackClient.queryTerminalTrack(queryTrackRequest, new GkdOnTrackListener() {
                            @Override
                            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {
                                if (queryTrackResponse.isSuccess()) {
                                    List<Track> tracks =  queryTrackResponse.getTracks();
                                    if (tracks != null && !tracks.isEmpty()) {
                                        boolean allEmpty = true;
                                        for (Track track : tracks) {
                                            List<Point> points = track.getPoints();
                                            if (points != null && points.size() > 0) {
                                                allEmpty = false;
                                                //drawTrackOnMap(points);
                                            }
                                        }
                                        List<Point> localPoint=tracks.get(tracks.size()-1).getPoints();
                                        drawTrackOnMap(localPoint);
                                        Log.w("latestTrack",Double.toString(tracks.get(tracks.size()-1).getDistance()));
                                        textView.setText(Double.toString(tracks.get(tracks.size()-1).getDistance()));
                                        //drawTrackOnMap(localPoint);
                                        if (allEmpty) {
                                            Toast.makeText(RunningResultActivity.this,
                                                    "所有轨迹都无轨迹点，请尝试放宽过滤限制，如：关闭绑路模式", Toast.LENGTH_SHORT).show();
                                        } else {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("共查询到").append(tracks.size()).append("条轨迹，每条轨迹行驶距离分别为：");
                                            for (Track track : tracks) {
                                                sb.append(track.getDistance()).append("m,");
                                                Log.w("trackd",Double.toString(track.getDistance()));
                                            }
                                            sb.deleteCharAt(sb.length() - 1);
                                            Toast.makeText(RunningResultActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RunningResultActivity.this, "未获取到轨迹", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RunningResultActivity.this, "查询历史轨迹失败，" + queryTrackResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RunningResultActivity.this, "Terminal不存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNetErrorHint(queryTerminalResponse.getErrorMsg());
                }
            }
        });
    }

    private void drawTrackOnMap(List<Point> points) {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE).width(20);
        if (points.size() > 0) {
            // 起点
            Point p = points.get(0);
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            endMarkers.add(textureMapView.getMap().addMarker(markerOptions));
        }
        if (points.size() > 1) {
            // 终点
            Point p = points.get(points.size() - 1);
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            endMarkers.add(textureMapView.getMap().addMarker(markerOptions));
        }
        for (Point p : points) {
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            polylineOptions.add(latLng);
            boundsBuilder.include(latLng);
        }
        Polyline polyline = textureMapView.getMap().addPolyline(polylineOptions);
        polylines.add(polyline);
        textureMapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
    }

    private void showNetErrorHint(String errorMsg) {
        Toast.makeText(this, "网络请求失败，错误原因: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

    //清空图片
    private void clearTracksOnMap() {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        for (Marker marker : endMarkers) {
            marker.remove();
        }
        endMarkers.clear();
        polylines.clear();
    }

    private void getALLDistance(TextView textView){
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(Constants.SERVICE_ID, Constants.TERMINAL_NAME), new GkdOnTrackListener() {
            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()) {
                    long terminalId = queryTerminalResponse.getTid();
                    if (terminalId > 0) {
                        long curr = System.currentTimeMillis();
                        DistanceRequest distanceRequest = new DistanceRequest(
                                Constants.SERVICE_ID,
                                terminalId,
                                curr - 12 * 60 * 60 * 1000, // 开始时间
                                curr,   // 结束时间
                                trackId  // 轨迹id
                        );
                        aMapTrackClient.queryDistance(distanceRequest, new GkdOnTrackListener() {
                            @Override
                            public void onDistanceCallback(DistanceResponse distanceResponse) {
                                if (distanceResponse.isSuccess()) {
                                    textView.setText(Double.toString(distanceResponse.getDistance()));
                                    Log.w("行驶里程查询成功，共行驶: " ,distanceResponse.getDistance() + "m");
                                } else {
                                    Log.w("行驶里程查询失败，",distanceResponse.getErrorMsg());
                                }
                            }
                        });
                    } else {
                        Log.w("终端不存在","1");
                    }
                } else {
                    Log.w("终端查询失败，" , queryTerminalResponse.getErrorMsg());
                }
            }
        });
    }

    /**
     * 带有地图渲染状态的截屏回调方法。
     * 根据返回的状态码，可以判断当前视图渲染是否完成。
     *
     * @param bitmap 调用截屏接口返回的截屏对象。
     * @param arg1 地图渲染状态， 1：地图渲染完成，0：未绘制完成
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap, int arg1) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if(null == bitmap){
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(
                    Environment.getExternalStorageDirectory() + "/test_"
                            + sdf.format(new Date()) + ".png");
            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            if (b)
                buffer.append("截屏成功 ");
            else {
                buffer.append("截屏失败 ");
            }
            if (arg1 != 0)
                buffer.append("地图渲染完成，截屏无网格");
            else {
                buffer.append( "地图未渲染完成，截屏有网格");
            }
            Log.w("1111111111111111111111",Environment.getExternalStorageDirectory() + "/test_"
                    + sdf.format(new Date()) + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        textureMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textureMapView.onDestroy();
    }
}
