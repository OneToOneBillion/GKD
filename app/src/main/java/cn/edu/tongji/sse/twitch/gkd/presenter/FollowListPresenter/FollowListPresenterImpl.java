package cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.model.FollowListModel.FollowListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.FollowListModel.IFollowListModel;
import cn.edu.tongji.sse.twitch.gkd.model.RankingListModel.IRankingListModel;
import cn.edu.tongji.sse.twitch.gkd.model.RankingListModel.RankingListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.IFollowListView;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.IRankingListView;

public class FollowListPresenterImpl implements IFollowListPresenter, IFollowListModel.OnShowFollowListener {
    private IFollowListModel iFollowListModel;
    private IFollowListView iFollowListView;

    public FollowListPresenterImpl(IFollowListView IFollowListView){
        iFollowListView=IFollowListView;
        iFollowListModel=new FollowListModelImpl(this);
    }

    public void showFollow(String userID, RecyclerView follow_list, Context context){
        iFollowListModel.showFollowList(userID,follow_list,context,this);
    }

    public void showFollowSuccess(){

    }
    public void showFollowFailed(){

    }
}
