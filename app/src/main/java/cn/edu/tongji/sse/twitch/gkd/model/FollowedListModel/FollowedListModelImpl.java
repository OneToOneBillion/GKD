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
import cn.edu.tongji.sse.twitch.gkd.bean.Followed;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
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
        BmobQuery<Followed> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sUsername",userID);
        bmobQuery.findObjects(new FindListener<Followed>() {
            @Override
            public void done(List<Followed> list, BmobException e) {
                if (e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> userList, BmobException e) {
                            String[] followed_item_avater=new String[list.get(0).getaFollowername().size()];
                            String[] followed_item_name=new String[list.get(0).getaFollowername().size()];
                            for(int i=0;i<list.get(0).getaFollowername().size();i++){
                                followed_item_avater[i]= userList.get(0).getAvater();
                                followed_item_name[i]=list.get(0).getaFollowername().get(i);
                            }
                            userInfoAdapter = new UserInfoAdapter(context,followed_item_avater,followed_item_name);
                            LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                            rank_manager.setOrientation(LinearLayoutManager.VERTICAL);
                            followed_list_recyclerView.setLayoutManager(rank_manager);
                            followed_list_recyclerView.setAdapter(userInfoAdapter);
                            Toast.makeText(getApplicationContext(),"展示运动数据成功",Toast.LENGTH_LONG).show();
                            onShowFollowedListener.showFollowedSuccess();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动数据失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowFollowedListener.showFollowedFailed();
                }
            }
        });
    }
}
