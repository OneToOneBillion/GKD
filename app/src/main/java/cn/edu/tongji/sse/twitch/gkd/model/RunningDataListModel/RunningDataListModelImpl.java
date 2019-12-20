package cn.edu.tongji.sse.twitch.gkd.model.RunningDataListModel;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Run;
import cn.edu.tongji.sse.twitch.gkd.model.PersonalModel.IPersonalModel;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.IPersonalPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningDataListPresenter.IRunningDataListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RankingListAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RunningDataAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;
import cn.edu.tongji.sse.twitch.gkd.view.RunningDataListView.IRunningDataListView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class RunningDataListModelImpl implements IRunningDataListModel {
    private IRunningDataListPresenter iRunningDataListPresenter;
    private IRunningDataListView iRunningDataListView;
    private RunningDataAdapter running_data_adapter;

    public RunningDataListModelImpl(IRunningDataListPresenter IRunningDataListPresenter){
        iRunningDataListPresenter=IRunningDataListPresenter;
    }

    public void ShowRunningDataList(String userID, RecyclerView running_data, Context context, IRunningDataListModel.OnShowRunningDataListener onShowRunningDataListener){
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
                        running_item_time[i]=list.get(i).getTime();
                    }
                    running_data_adapter = new RunningDataAdapter(context,running_item_num,running_item_distance,running_item_timelength,running_item_time);
                    LinearLayoutManager run_manager = new LinearLayoutManager(context);
                    run_manager.setOrientation(RecyclerView.VERTICAL);
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
}
