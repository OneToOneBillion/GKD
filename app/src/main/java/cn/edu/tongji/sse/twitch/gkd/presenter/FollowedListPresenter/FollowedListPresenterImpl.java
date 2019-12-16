package cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.model.FollowListModel.FollowListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.FollowListModel.IFollowListModel;
import cn.edu.tongji.sse.twitch.gkd.model.FollowedListModel.FollowedListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.FollowedListModel.IFollowedListModel;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter.IFollowListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.IFollowListView;
import cn.edu.tongji.sse.twitch.gkd.view.FollowedListView.IFollowedListView;

public class FollowedListPresenterImpl implements IFollowedListPresenter, IFollowedListModel.OnShowFollowedListener {
    private IFollowedListModel iFollowedListModel;
    private IFollowedListView iFollowedListView;

    public FollowedListPresenterImpl(IFollowedListView IFollowedListView){
        iFollowedListView=IFollowedListView;
        iFollowedListModel=new FollowedListModelImpl(this);
    }

    public void showFollowed(String userID, RecyclerView followed_list, Context context){
        iFollowedListModel.showFollowedList(userID,followed_list,context,this);
    }

    public void showFollowedSuccess(){

    }
    public void showFollowedFailed(){

    }
}
