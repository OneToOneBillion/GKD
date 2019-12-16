package cn.edu.tongji.sse.twitch.gkd.model.RunningModel;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Run;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.IRunningPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.RunningPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.IRunningView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class RunningModelImpl implements IRunningModel{
    private IRunningView iRunningView;
    private IRunningPresenter iRunningPresenter;

    public RunningModelImpl(IRunningPresenter IRunningPresenter){
        iRunningPresenter=IRunningPresenter;
    }

    public void addRunData(String userID,long run_time,String rundistance,OnAddlistener onAddlistener){
        double run_distance = Double.valueOf(rundistance);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Run run=new Run();
        run.setsUsername(userID);
        run.setiRunTime(run_time);
        run.setsRunDistance(run_distance);
        run.setTime(formatter.format(date));
        run.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                    onAddlistener.AddSuccess();
                    BmobQuery<User> bmobQuery=new BmobQuery<>();
                    bmobQuery.addWhereEqualTo("username",userID);
                    bmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null){
                                User user =new User();
                                user.setUsername(userID);
                                user.setPassword(list.get(0).getPassword());
                                user.setFollow_num(list.get(0).getFollow_num());
                                user.setFollowed_num(list.get(0).getFollowed_num());
                                user.setPost_num(list.get(0).getPost_num());
                                user.setPunchin_num(list.get(0).getPunchin_num());
                                user.setTarget(list.get(0).getTarget());
                                user.setAvater(list.get(0).getAvater());
                                user.setRun_distance(list.get(0).getRun_distance()+run_distance);
                                user.setRun_times(list.get(0).getRun_times()+run_time);
                                user.update(list.get(0).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){

                                        }else{
                                            onAddlistener.AddFailed();
                                        }
                                    }
                                });
                            }else{
                                onAddlistener.AddFailed();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    onAddlistener.AddFailed();
                }
            }
        });
    }

    public String findUsernamebyID(String userID,OnAddlistener onAddlistener){
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("user_id",userID);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    //return list.get(0).getUsername();
                }
            }
        });
        return "qwer";
    }
}
