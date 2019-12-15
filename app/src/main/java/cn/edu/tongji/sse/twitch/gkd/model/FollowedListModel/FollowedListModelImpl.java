package cn.edu.tongji.sse.twitch.gkd.model.FollowedListModel;

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
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter.IFollowedListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RankingListAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.UserInfoAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.FollowedListView.IFollowedListView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class FollowedListModelImpl implements IFollowedListModel {
    private IFollowedListView iFollowedListView;
    private IFollowedListPresenter iFollowedListPresenter;
    private UserInfoAdapter userInfoAdapter;

    public FollowedListModelImpl(IFollowedListPresenter IFollowedListPresenter){
        iFollowedListPresenter=IFollowedListPresenter;
    }

    public void showFollowedList(String userID, RecyclerView followed_list_recyclerView, Context context, IFollowedListModel.OnShowFollowedListener onShowFollowedListener){
        BmobQuery<Follow> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sUsername",userID);
        bmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e==null){
                    int[] followed_item_avater=new int[list.size()];
                    String[] followed_item_name=new String[list.size()];
                    for (int i=0;i< list.size();i++){
                        for(int j=0;j<list.get(i).getaFollowername().size();j++){
                            followed_item_avater[i]= R.drawable.hhh;
                            followed_item_name[i]="张"+i+"伟";
                        }
                    }
                    userInfoAdapter = new UserInfoAdapter(context,followed_item_avater,followed_item_name);
                    LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                    rank_manager.setOrientation(LinearLayoutManager.VERTICAL);
                    followed_list_recyclerView.setLayoutManager(rank_manager);
                    followed_list_recyclerView.setAdapter(userInfoAdapter);
                    Toast.makeText(getApplicationContext(),"展示运动数据成功",Toast.LENGTH_LONG).show();
                    onShowFollowedListener.showFollowedSuccess();
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动数据失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowFollowedListener.showFollowedFailed();
                }
            }
        });
    }
}
