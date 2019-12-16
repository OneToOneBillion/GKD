package cn.edu.tongji.sse.twitch.gkd.model.SignUpModel;

import android.content.Intent;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.Followed;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.SignUpPresenter.ISignUpPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.SignUpView.ISignUpView;
import cn.edu.tongji.sse.twitch.gkd.view.SignUpView.SignUpActivity;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.UserLoginActivity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class SignUpModelImpl implements ISignUpModel{
    private ISignUpView iSignUpView;
    private ISignUpPresenter iSignUpPresenter;

    public SignUpModelImpl(ISignUpPresenter ISignUpPresenter){
        iSignUpPresenter=ISignUpPresenter;
    }

    public void createUser(String username,String password,OnCreateUserListener onCreateUserListener){
        User u1=new User();
        u1.setUsername(username);
        u1.setPassword(password);
        u1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                    Follow follow=new Follow();
                    follow.setsUsername(username);
                    follow.addaFollowername(username);
                    follow.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            
                        }
                    });

                    Followed followed=new Followed();
                    followed.setsUsername(username);
                    followed.addaFollowername(username);
                    followed.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
