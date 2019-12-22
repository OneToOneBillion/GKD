package cn.edu.tongji.sse.twitch.gkd.model.UserModel;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.IUserLoginPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.IUserLoginView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class UserModelImpl implements IUserModel {
    private IUserLoginPresenter mIUserLoginPresenter;

    public UserModelImpl(IUserLoginPresenter IUserLoginPresenter){
        mIUserLoginPresenter = IUserLoginPresenter;
    }

    @Override
    public void login(String username, String password,IUserModel.OnLoginListener listener){
        new Thread(){
            @Override
            public void run(){
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                BmobQuery<User> query =new BmobQuery<>();
                query.addWhereEqualTo("username",username);
                query.addWhereEqualTo("password",password);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null){
                            User user=new User();
                            user.setUsername(username);
                            user.setPassword(password);
                            if(list.size()>0){
                                listener.loginSuccess();
                            }
                            else {
                                listener.loginFailed();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"查询失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();
    }
}
