package cn.edu.tongji.sse.twitch.gkd.presenter;

public interface IUserLoginPresenter {
    void doLogin(String un, String pwd);
    void clear();
    void safeAccount(String un, String pwd);
}
