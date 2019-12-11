package cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter;

public interface IAddFriendPresenter {
    void addNewFriend(String userID,String Followername);
    void deleteThisFriend(String userID,String Followername);
}
