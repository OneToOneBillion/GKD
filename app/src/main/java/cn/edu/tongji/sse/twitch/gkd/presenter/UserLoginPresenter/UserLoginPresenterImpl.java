package cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter;

import android.os.Handler;

import cn.edu.tongji.sse.twitch.gkd.model.UserModel.IUserModel;
import cn.edu.tongji.sse.twitch.gkd.model.UserModel.UserModelImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.IUserLoginPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.IUserLoginView;

public class UserLoginPresenterImpl implements IUserLoginPresenter, IUserModel.OnLoginListener {
    private IUserLoginView mIUserLoginView;
    private IUserModel mIUserModel;

    private Handler mHandler = new Handler();

    public UserLoginPresenterImpl(IUserLoginView IUserLoginView){
        mIUserLoginView = IUserLoginView;
        mIUserModel = new UserModelImpl(this);
    }

    @Override
    public void doLogin(String un, String pwd){
        mIUserModel.login(un, pwd,this);
    }

    @Override
    public void loginSuccess(){
        mHandler.post(new Runnable() {
            @Override
            public void run(){
                mIUserLoginView.toRunningActivity();
            }
        });
    }

    @Override
    public void loginFailed(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mIUserLoginView.showFailedError();
            }
        });
    }
}
