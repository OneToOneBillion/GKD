package cn.edu.tongji.sse.twitch.gkd.presenter.RankingListPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.RankingListModel.IRankingListModel;
import cn.edu.tongji.sse.twitch.gkd.model.RankingListModel.RankingListModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.IRankingListView;

public class RankingListPresenterImpl implements IRankingListPresenter, IRankingListModel.OnShowRankingListener {
    private IRankingListModel iRankingListModel;
    private IRankingListView iRankingListView;

    public RankingListPresenterImpl(IRankingListView IRankingListView){
        iRankingListView=IRankingListView;
        iRankingListModel=new RankingListModelImpl(this);
    }

    public void showRanking(String userID, RecyclerView ranking_list, Context context){
        iRankingListModel.showRankingList(userID,ranking_list,context,this);
    }

    public void showRankingSuccess(){

    }
    public void showRankingFailed(){

    }
}
