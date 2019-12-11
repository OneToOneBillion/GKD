package cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel;

public interface IAddFriendModel {
    void addFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener);
    void deleteFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener);

    public interface OnAddFriendListener{
        void addFriendSuccess();
        void addFriendFailed();
    }
}
