package cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel.AddFriendModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel.IAddFriendModel;
import cn.edu.tongji.sse.twitch.gkd.model.PostModel.IPostModel;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.IPostPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.AddFriendView.IAddFriendView;

public class AddFriendPresenterImpl implements IAddFriendPresenter, IAddFriendModel.OnAddFriendListener{
    private IAddFriendView iAddFriendView;
    private IAddFriendModel iAddFriendModel;

    public AddFriendPresenterImpl(IAddFriendView IAddFriendView){
        iAddFriendView=IAddFriendView;
        iAddFriendModel=new AddFriendModelImpl(this);
    }

    public void searchforFriend(String userID,String Followername){
        iAddFriendModel.searchFriend(userID,Followername,this);
    }

    public void addNewFriend(String userID,String Followername){
        iAddFriendModel.addFriend(userID,Followername,this);
    }

    public void deleteThisFriend(String userID,String Followername){
        iAddFriendModel.deleteFriend(userID,Followername,this);
    }

    public void addFriendSuccess(String username,String avater,boolean isFollowed){
        if(isFollowed){
            iAddFriendView.changeSwitchChecked();
        }
        else {
            iAddFriendView.changeSwitchUnchecked();
        }
        iAddFriendView.setFriendName(username);
        iAddFriendView.setFriendAvater(avater);
    }
    public void addFriendFailed(String failedInfo){
        iAddFriendView.setFriendName(failedInfo);
    }
}
