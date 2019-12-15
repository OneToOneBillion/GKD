package cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel;

public interface IAddFriendModel {
    void searchFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener);
    void addFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener);
    void deleteFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener);

    public interface OnAddFriendListener{
        void addFriendSuccess(String username,String avater,boolean isFollowed);
        void addFriendFailed(String failedInfo);
    }
}
