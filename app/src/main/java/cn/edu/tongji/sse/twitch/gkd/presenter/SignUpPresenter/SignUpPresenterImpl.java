package cn.edu.tongji.sse.twitch.gkd.presenter.SignUpPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.SignUpModel.ISignUpModel;
import cn.edu.tongji.sse.twitch.gkd.model.SignUpModel.SignUpModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.SignUpView.ISignUpView;

public class SignUpPresenterImpl implements ISignUpPresenter,ISignUpModel.OnCreateUserListener{
    private ISignUpView iSignUpView;
    private ISignUpModel iSignUpModel;

    public SignUpPresenterImpl(ISignUpView ISignUpView){
        iSignUpView=ISignUpView;
        iSignUpModel=new SignUpModelImpl(this);
    }

    public void createNewUser(String username,String password){
        iSignUpModel.createUser(username,password,this);
    }

    public void createUserSuccess(){

    }
    public void createUserFailed(){

    }
}
