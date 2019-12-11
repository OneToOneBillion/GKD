package cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel;

public interface IChangeInfoModel {
    void saveChange(String userID,String neckname,String target,OnChangeListener onChangeListener);

    public interface OnChangeListener{
        void changeSuccess();
        void changeFailed();
    }
}
