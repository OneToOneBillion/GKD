package cn.edu.tongji.sse.twitch.gkd.view.UserLoginView;

public interface IUserLoginView {
    //获取用户名输入框的内容
    String getUserName();
    //获取密码输入框的内容
    String getPassword();
    //获取记住密码勾选框状态
    Boolean getCbRPState();
    //获取自动登陆勾选框状态
    Boolean getCbALState();
    //跳转到跑步界面
    void toRunningActivity();
    //跳转到注册界面
    void toSignUpActivity();
    void showFailedError();
    //记住账号信息
    void saveAccount(String un, String pwd);
    //读取保存账号的用户名
    String readSaveUn();
    //读取保存账号的密码
    String readSavePwd();
    //存储两个勾选框状态
    void saveCbState(Boolean cbRPState, Boolean cbALState);
    //读取保存的记住密码勾选框的状态
    Boolean readSaveCbRPState();
    //读取保存的自动登陆勾选框的状态
    Boolean readSaveCbALState();
}
