package cn.edu.tongji.sse.twitch.gkd.model.PersonalModel;

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
import cn.edu.tongji.sse.twitch.gkd.bean.Run;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.IPersonalPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RankingListAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RunningDataAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class PersonalModelImpl implements IPersonalModel{
    private IPersonalView iPersonalView;
    private IPersonalPresenter iPersonalPresenter;
    private RunningDataAdapter running_data_adapter;
    private RankingListAdapter ranking_list_adapter;

    public PersonalModelImpl(IPersonalPresenter IPersonalPresenter){
        iPersonalPresenter=IPersonalPresenter;
    }

    public void ShowRunningDataList(String userID, RecyclerView running_data, Context context, OnShowRunningDataListener onShowRunningDataListener){
        BmobQuery<Run> query=new BmobQuery<>();
        query.addWhereEqualTo("sUsername", userID);
        query.findObjects(new FindListener<Run>() {
            @Override
            public void done(List<Run> list, BmobException e) {
                if(e==null){
                    int[] running_item_num=new int[list.size()];
                    double[] running_item_distance=new double[list.size()];
                    long[] running_item_timelength=new long[list.size()];
                    String[] running_item_time=new String[list.size()];
                    for (int i=0;i< list.size();i++){
                        running_item_num[i]=i;
                        running_item_distance[i]=list.get(i).getsRunDistance();
                        running_item_timelength[i]=list.get(i).getiRunTime();
                        running_item_time[i]=list.get(i).getTime();
                    }
                    running_data_adapter = new RunningDataAdapter(context,running_item_num,running_item_distance,running_item_timelength,running_item_time);
                    LinearLayoutManager run_manager = new LinearLayoutManager(context);
                    run_manager.setOrientation(RecyclerView.VERTICAL);
                    running_data.setLayoutManager(run_manager);
                    running_data.setAdapter(running_data_adapter);
                    onShowRunningDataListener.ShowRunningDataSuccess();
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动数据失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowRunningDataListener.ShowRunningDataFailed();
                }
            }
        });
    }
    public void ShowRankingList(String userID,RecyclerView ranking_list, Context context,OnShowRankingListener onShowRankingListener){
        BmobQuery<Follow> queryRanking=new BmobQuery<>();
        queryRanking.addWhereEqualTo("sUsername", userID);
        queryRanking.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> followList, BmobException e) {
                if (e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.order("-run_distance");
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            String[] ranking_item_rank=new String[followList.get(0).getaFollowername().size()];
                            String[] ranking_item_avater=new String[followList.get(0).getaFollowername().size()];
                            String[] ranking_item_name=new String[followList.get(0).getaFollowername().size()];
                            for (int i=0;i< followList.get(0).getaFollowername().size();i++){
                                int m=i+1;
                                ranking_item_rank[i]="第"+m+"名";
                                ranking_item_avater[i]= followList.get(0).getaFollowerIcon().get(i);
                                ranking_item_name[i]=followList.get(0).getaFollowername().get(i);

                            }
                            ranking_list_adapter = new RankingListAdapter(context,ranking_item_rank,ranking_item_avater,ranking_item_name);
                            LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                            rank_manager.setOrientation(RecyclerView.VERTICAL);
                            ranking_list.setLayoutManager(rank_manager);
                            ranking_list.setAdapter(ranking_list_adapter);
                            onShowRankingListener.ShowRankingSuccess();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动排行榜失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowRankingListener.ShowRankingFailed();
                }
            }
        });
    }
}
