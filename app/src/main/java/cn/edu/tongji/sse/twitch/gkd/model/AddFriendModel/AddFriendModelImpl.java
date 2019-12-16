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

    //查询好友
    public void searchFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener){
        //先查找查找的ID有没有被注册过
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",Followername);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if(list.size()==0){//该用户没有被注册，返回"用户不存在"
                        onAddFriendListener.addFriendFailed("用户不存在");
                    }
                    else if(list.size()==1){//用户被注册过，"用户存在"，查询原先有没有关注过
                        String avater=list.get(0).getAvater();
                        BmobQuery<Follow> followBmobQuery=new BmobQuery<>();
                        followBmobQuery.addWhereEqualTo("sUsername",userID);
                        followBmobQuery.findObjects(new FindListener<Follow>() {
                            @Override
                            public void done(List<Follow> querylist, BmobException e) {
                                if(e==null){
                                    boolean isFollowed=false;
                                    for(int i=0;i<querylist.get(0).getaFollowername().size();i++){
                                        if(Followername.equals(querylist.get(0).getaFollowername().get(i))){
                                            isFollowed=true;
                                        }
                                    }
                                    //用户原先就关注过，返回查询到的ID，avater，true（被关注过）
                                    //用户原先没有关注过，返回查询到的ID，avater，false（没有被关注过）
                                    onAddFriendListener.addFriendSuccess(Followername,avater,isFollowed);
                                }
                            }
                        });
                    }
                } else {
                    onAddFriendListener.addFriendFailed("查询失败");
                }
            }
        });
    }

    public void addFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener){
        BmobQuery<Follow> followBmobQuery=new BmobQuery<>();
        followBmobQuery.addWhereEqualTo("sUsername",userID);
        followBmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> querylist, BmobException e) {
                if(e==null){
                    Follow follow=new Follow();
                    follow.addaFollowername(Followername);
                    follow.update(querylist.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                                userBmobQuery.addWhereEqualTo("username",userID);
                                userBmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        User user=new User();
                                        user.setUsername(userID);
                                        user.setPassword(list.get(0).getPassword());
                                        user.setFollow_num(list.get(0).getFollow_num()+1);
                                        user.setFollowed_num(list.get(0).getFollowed_num());
                                        user.setPost_num(list.get(0).getPost_num());
                                        user.setPunchin_num(list.get(0).getPunchin_num());
                                        user.setAvater(list.get(0).getAvater());
                                        user.setTarget(list.get(0).getTarget());
                                        user.setRun_times(list.get(0).getRun_times());
                                        user.setRun_distance(list.get(0).getRun_distance());
                                        user.update(list.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                });
                                BmobQuery<User> followBmobQuery=new BmobQuery<>();
                                followBmobQuery.addWhereEqualTo("username",Followername);
                                followBmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        User user=new User();
                                        user.setUsername(Followername);
                                        user.setPassword(list.get(0).getPassword());
                                        user.setFollow_num(list.get(0).getFollow_num());
                                        user.setFollowed_num(list.get(0).getFollowed_num()+1);
                                        user.setPost_num(list.get(0).getPost_num());
                                        user.setPunchin_num(list.get(0).getPunchin_num());
                                        user.setAvater(list.get(0).getAvater());
                                        user.setTarget(list.get(0).getTarget());
                                        user.setRun_times(list.get(0).getRun_times());
                                        user.setRun_distance(list.get(0).getRun_distance());
                                        user.update(list.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    follow.update(list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                                userBmobQuery.addWhereEqualTo("username",userID);
                                userBmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        User user=new User();
                                        user.setUsername(userID);
                                        user.setPassword(list.get(0).getPassword());
                                        user.setFollow_num(list.get(0).getFollow_num()-1);
                                        user.setFollowed_num(list.get(0).getFollowed_num());
                                        user.setPost_num(list.get(0).getPost_num());
                                        user.setPunchin_num(list.get(0).getPunchin_num());
                                        user.setAvater(list.get(0).getAvater());
                                        user.setTarget(list.get(0).getTarget());
                                        user.update(list.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                });
                                BmobQuery<User> followBmobQuery=new BmobQuery<>();
                                followBmobQuery.addWhereEqualTo("username",Followername);
                                followBmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        User user=new User();
                                        user.setUsername(Followername);
                                        user.setPassword(list.get(0).getPassword());
                                        user.setFollow_num(list.get(0).getFollow_num());
                                        user.setFollowed_num(list.get(0).getFollowed_num()-1);
                                        user.setPost_num(list.get(0).getPost_num());
                                        user.setPunchin_num(list.get(0).getPunchin_num());
                                        user.setAvater(list.get(0).getAvater());
                                        user.setTarget(list.get(0).getTarget());
                                        user.update(list.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "取消关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "取消关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
