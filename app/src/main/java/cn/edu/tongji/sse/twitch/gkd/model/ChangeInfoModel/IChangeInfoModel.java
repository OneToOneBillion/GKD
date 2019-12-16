package cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel;

public interface IChangeInfoModel {
    void saveChange(String userID,String avater,String target,OnChangeListener onChangeListener);

    public interface OnChangeListener{
        void changeSuccess(String userID,String avater,String target);
        void changeFailed();
    }
}
