package cn.edu.tongji.sse.twitch.gkd.model.SignUpModel;

public interface ISignUpModel {
    void createUser(String username,String password,OnCreateUserListener onCreateUserListener);

    public interface OnCreateUserListener{
        void createUserSuccess();
        void createUserFailed();
    }
}
