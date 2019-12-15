package cn.edu.tongji.sse.twitch.gkd.model.RunningModel;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
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

    public void addRunData(String userID,long run_time,OnAddlistener onAddlistener){
        Run run=new Run();
        run.setsUsername(userID);
        run.setiRunTime(run_time);
        run.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                    onAddlistener.AddSuccess();
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
