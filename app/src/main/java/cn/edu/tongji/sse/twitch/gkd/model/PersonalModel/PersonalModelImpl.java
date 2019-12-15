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
                        running_item_distance[i]=12.34;
                        running_item_timelength[i]=2500;
                        running_item_time[i]="2019/12/12 12:"+i;
                    }
                    running_data_adapter = new RunningDataAdapter(context,running_item_num,running_item_distance,running_item_timelength,running_item_time);
                    LinearLayoutManager run_manager = new LinearLayoutManager(context);
                    run_manager.setOrientation(LinearLayoutManager.VERTICAL);
                    running_data.setLayoutManager(run_manager);
                    running_data.setAdapter(running_data_adapter);
                    Toast.makeText(getApplicationContext(),"展示运动数据成功",Toast.LENGTH_LONG).show();
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
            public void done(List<Follow> list, BmobException e) {
                if (e==null){
                    String[] ranking_item_rank=new String[list.size()];
                    int[] ranking_item_avater=new int[list.size()];
                    String[] ranking_item_name=new String[list.size()];
                    for (int i=0;i< list.size();i++){
                        for(int j=0;j<list.get(i).getaFollowername().size();j++){
                            ranking_item_rank[i]="第"+i+"名";
                            ranking_item_avater[i]= R.drawable.hhh;
                            ranking_item_name[i]="张"+i+"伟";
                        }
                    }
                    ranking_list_adapter = new RankingListAdapter(context,ranking_item_rank,ranking_item_avater,ranking_item_name);
                    LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                    rank_manager.setOrientation(LinearLayoutManager.VERTICAL);
                    ranking_list.setLayoutManager(rank_manager);
                    ranking_list.setAdapter(ranking_list_adapter);
                    Toast.makeText(getApplicationContext(),"展示运动排行榜成功",Toast.LENGTH_LONG).show();
                    onShowRankingListener.ShowRankingSuccess();
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动排行榜失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowRankingListener.ShowRankingFailed();
                }
            }
        });
    }
}
