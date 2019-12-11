package cn.edu.tongji.sse.twitch.gkd.model.PersonalModel;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.Run;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.IPersonalPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class PersonalModelImpl implements IPersonalModel{
    private IPersonalView iPersonalView;
    private IPersonalPresenter iPersonalPresenter;
    private RecyclerViewAdapter running_data_adapter,ranking_list_adapter;

    public PersonalModelImpl(IPersonalPresenter IPersonalPresenter){
        iPersonalPresenter=IPersonalPresenter;
    }

    public void ShowRunningDataList(String userID, List<String> show_running_data_list, RecyclerView running_data, Context context, OnShowRunningDataListener onShowRunningDataListener){
        /*BmobQuery<Run> query=new BmobQuery<>();
        query.addWhereEqualTo("sUsername", "qwer");
        query.findObjects(new FindListener<Run>() {
            @Override
            public void done(List<Run> list, BmobException e) {
                if(e==null){
                    for (int i=0;i< list.size();i++){
                        show_running_data_list.add("第"+(i+1)+"次运动："+list.get(i).getiRunTime());
                    }
                    running_data_adapter = new RecyclerViewAdapter(context,show_running_data_list);
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
        });*/
    }
    public void ShowRankingList(String userID,List<String> show_ranking_list,RecyclerView ranking_list, Context context,OnShowRankingListener onShowRankingListener){
        /*BmobQuery<Follow> queryRanking=new BmobQuery<>();
        queryRanking.addWhereEqualTo("sUsername", "qwer");
        queryRanking.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e==null){
                    for (int i=0;i< list.size();i++){
                        for(int j=0;j<list.get(i).getaFollowername().size();j++){
                            show_ranking_list.add("第"+(i+1)+"名  "+list.get(i).getaFollowername().get(j));
                        }
                    }
                    ranking_list_adapter = new RecyclerViewAdapter(context,show_ranking_list);
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
        });*/
    }
}
