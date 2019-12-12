package cn.edu.tongji.sse.twitch.gkd.view.SystemSettingView;

public interface ISystemSettingView {
    //保存系统设置
    void saveSystemSetting(String l);
    //获取语言选择控件内容
    String getLanguageSetting();
    //返回个人界面
    void toPersonalView();
    //刷新系统设置界面
    void refreshSysSettingView();
}
