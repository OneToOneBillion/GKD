package cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.AddFriendPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.IAddFriendPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.AddFriendView.IAddFriendView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class AddFriendModelImpl implements IAddFriendModel{
    private IAddFriendPresenter iAddFriendPresenter;
    private IAddFriendView iAddFriendView;

    public AddFriendModelImpl(IAddFriendPresenter IAddFriendPresenter){
        iAddFriendPresenter=IAddFriendPresenter;
    }

    public void addFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener){
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",userID);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "查询成功："+list.size(), Toast.LENGTH_SHORT).show();
                    Follow follow=new Follow();
                    follow.addaFollowername(Followername);
                    follow.update("93708a4772", new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "更新成功：", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener){
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",userID);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "查询成功："+list.size(), Toast.LENGTH_SHORT).show();
                    Follow follow=new Follow();
                    follow.deleteFollowername(Followername);
                    follow.update("93708a4772", new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "更新成功：", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
