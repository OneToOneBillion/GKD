package cn.edu.tongji.sse.twitch.gkd.view.UserLoginView;

public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void clearUserName();
    void clearPassword();
    void toMainActivity();
    void showFailedError();
    //记住账号信息
    void saveAccount(String un, String pwd);
    //读取记住账号的用户名信息
    String readSaveUn();
    //读取记住账号的密码信息
    String readSavePwd();
    //存储两个勾选框状态
    void saveCbState();
}
