package cn.edu.tongji.sse.twitch.gkd.model.RankingListModel;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.IPersonalModel;
import cn.edu.tongji.sse.twitch.gkd.presenter.RankingListPresenter.IRankingListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RankingListAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.IRankingListView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class RankingListModelImpl implements IRankingListModel {
    private IRankingListPresenter iRankingListPresenter;
    private IRankingListView iRankingListView;
    private RankingListAdapter rankingListAdapter;

    public RankingListModelImpl(IRankingListPresenter IRankingListPresenter){
        iRankingListPresenter=IRankingListPresenter;
    }

    public void showRankingList(String userID, RecyclerView ranking_list_recyclerView, Context context,
                                IRankingListModel.OnShowRankingListener onShowRankingListener){
        BmobQuery<Follow> queryRanking=new BmobQuery<>();
        queryRanking.addWhereEqualTo("sUsername", userID);
        queryRanking.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> userList, BmobException e) {
                            String[] ranking_item_rank=new String[list.get(0).getaFollowername().size()];
                            String[] ranking_item_avater=new String[list.get(0).getaFollowername().size()];
                            String[] ranking_item_name=new String[list.get(0).getaFollowername().size()];
                            for (int i=0;i< list.get(0).getaFollowername().size();i++){
                                int m=i+1;
                                ranking_item_rank[i]="第"+m+"名";
                                ranking_item_avater[i]= list.get(0).getaFollowerIcon().get(i);
                                ranking_item_name[i]=list.get(0).getaFollowername().get(i);
                            }
                            rankingListAdapter = new RankingListAdapter(context,ranking_item_rank,ranking_item_avater,ranking_item_name);
                            LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                            rank_manager.setOrientation(RecyclerView.VERTICAL);
                            ranking_list_recyclerView.setLayoutManager(rank_manager);
                            ranking_list_recyclerView.setAdapter(rankingListAdapter);
                            onShowRankingListener.showRankingSuccess();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动排行榜失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowRankingListener.showRankingFailed();
                }
            }
        });
    }
}
