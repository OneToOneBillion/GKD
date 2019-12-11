package cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter;

import android.os.Handler;

import cn.edu.tongji.sse.twitch.gkd.model.UserModel.IUserModel;
import cn.edu.tongji.sse.twitch.gkd.model.UserModel.UserModelImpl;
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
        mIUserLoginView.showLoading();
        mIUserModel.login(un, pwd,this);
    }

    @Override
    public void loginSuccess(){
        mHandler.post(new Runnable() {
            @Override
            public void run(){
                mIUserLoginView.hideLoading();
                mIUserLoginView.toMainActivity();
            }
        });
    }

    @Override
    public void loginFailed(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mIUserLoginView.hideLoading();
                mIUserLoginView.showFailedError();
            }
        });
    }

    @Override
    public void clear(){
        mIUserLoginView.clearUserName();
        mIUserLoginView.clearPassword();
    }

    @Override
    public void safeAccount(String un, String pwd){
        ;
    }

}
