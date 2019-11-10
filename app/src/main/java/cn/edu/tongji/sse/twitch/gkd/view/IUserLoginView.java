package cn.edu.tongji.sse.twitch.gkd.view;

public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void clearUserName();
    void clearPassword();
    void showLoading();
    void hideLoading();
    void toMainActivity();
    void showFailedError();
    //记住账号
    void saveAccount();
    //读取记住账号的内容
    void readAccount();
}
