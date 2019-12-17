package cn.edu.tongji.sse.twitch.gkd.model.FollowListModel;

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
import cn.edu.tongji.sse.twitch.gkd.model.FollowedListModel.IFollowedListModel;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter.IFollowListPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter.IFollowedListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.UserInfoAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.IFollowListView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class FollowListModelImpl implements IFollowListModel {
    private IFollowListView iFollowListView;
    private IFollowListPresenter iFollowListPresenter;
    private UserInfoAdapter userInfoAdapter;

    public FollowListModelImpl(IFollowListPresenter IFollowListPresenter){
        iFollowListPresenter=IFollowListPresenter;
    }

    public void showFollowList(String userID, RecyclerView follow_list_recyclerView, Context context, IFollowListModel.OnShowFollowListener onShowFollowListener){
        BmobQuery<Follow> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sUsername",userID);
        bmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> userList, BmobException e) {
                            String[] follow_item_avater = new String[list.get(0).getaFollowername().size()];
                            String[] follow_item_name = new String[list.get(0).getaFollowername().size()];
                            for(int i=0;i<list.get(0).getaFollowername().size();i++){
                                follow_item_avater[i]= userList.get(0).getAvater();
                                follow_item_name[i]=list.get(0).getaFollowername().get(i);
                            }
                            userInfoAdapter = new UserInfoAdapter(context, follow_item_avater, follow_item_name);
                            LinearLayoutManager rank_manager = new LinearLayoutManager(context);
                            rank_manager.setOrientation(LinearLayoutManager.VERTICAL);
                            follow_list_recyclerView.setLayoutManager(rank_manager);
                            follow_list_recyclerView.setAdapter(userInfoAdapter);
                            Toast.makeText(getApplicationContext(), "展示运动数据成功", Toast.LENGTH_LONG).show();
                            onShowFollowListener.showFollowSuccess();
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(),"展示运动数据失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    onShowFollowListener.showFollowFailed();
                }
            }
        });
    }
}
