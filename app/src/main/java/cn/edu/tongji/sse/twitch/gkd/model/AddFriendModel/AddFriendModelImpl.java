package cn.edu.tongji.sse.twitch.gkd.model.AddFriendModel;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.Followed;
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
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            BmobQuery<User> follownameBmobQuery=new BmobQuery<>();
                            follownameBmobQuery.addWhereEqualTo("username",Followername);
                            follownameBmobQuery.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> follownamelist, BmobException e) {
                                    boolean isFollowed = false;
                                    for (int i = 0; i < querylist.get(0).getaFollowername().size(); i++) {
                                        if (Followername.equals(querylist.get(0).getaFollowername().get(i))) {
                                            isFollowed = true;
                                        }
                                    }
                                    if (!isFollowed) {
                                        Follow follow = new Follow();
                                        follow.setsUsername(userID);
                                        follow.setaFollowername(querylist.get(0).getaFollowername());
                                        follow.addaFollowername(querylist.get(0).getaFollowername(), Followername);
                                        follow.setaFollowerIcon(querylist.get(0).getaFollowerIcon());
                                        follow.addaFollowerIcon(querylist.get(0).getaFollowerIcon(), follownamelist.get(0).getAvater());
                                        follow.update(querylist.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    User user = new User();
                                                    user.setUsername(userID);
                                                    user.setPassword(list.get(0).getPassword());
                                                    user.setFollow_num(list.get(0).getFollow_num() + 1);
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
                                                    User follower = new User();
                                                    follower.setUsername(Followername);
                                                    follower.setPassword(follownamelist.get(0).getPassword());
                                                    follower.setFollow_num(follownamelist.get(0).getFollow_num());
                                                    follower.setFollowed_num(follownamelist.get(0).getFollowed_num() + 1);
                                                    follower.setPost_num(follownamelist.get(0).getPost_num());
                                                    follower.setPunchin_num(follownamelist.get(0).getPunchin_num());
                                                    follower.setAvater(follownamelist.get(0).getAvater());
                                                    follower.setTarget(follownamelist.get(0).getTarget());
                                                    follower.setRun_times(follownamelist.get(0).getRun_times());
                                                    follower.setRun_distance(follownamelist.get(0).getRun_distance());
                                                    follower.update(follownamelist.get(0).getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BmobQuery<Followed> followedBmobQuery=new BmobQuery<>();
        followedBmobQuery.addWhereEqualTo("sUsername",Followername);
        followedBmobQuery.findObjects(new FindListener<Followed>() {
            @Override
            public void done(List<Followed> list, BmobException e) {
                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                userBmobQuery.addWhereEqualTo("username",userID);
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> userList, BmobException e) {
                        boolean isFollowed = false;
                        for (int i = 0; i < list.get(0).getaFollowername().size(); i++) {
                            if (userID.equals(list.get(0).getaFollowername().get(i))) {
                                isFollowed = true;
                            }
                        }
                        if (!isFollowed) {
                            Followed followed = new Followed();
                            followed.setsUsername(Followername);
                            followed.setaFollowername(list.get(0).getaFollowername());
                            followed.addaFollowername(list.get(0).getaFollowername(), userID);
                            followed.setaFollowerIcon(list.get(0).getaFollowerIcon());
                            followed.addaFollowerIcon(list.get(0).getaFollowerIcon(),userList.get(0).getAvater());
                            followed.update(list.get(0).getObjectId(),new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void deleteFriend(String userID,String Followername,OnAddFriendListener onAddFriendListener){
        BmobQuery<Follow> followBmobQuery=new BmobQuery<>();
        followBmobQuery.addWhereEqualTo("sUsername",userID);
        followBmobQuery.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> querylist, BmobException e) {
                if(e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            BmobQuery<User> follownameBmobQuery=new BmobQuery<>();
                            follownameBmobQuery.addWhereEqualTo("username",Followername);
                            follownameBmobQuery.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> follownamelist, BmobException e) {
                                    boolean isFollowed = false;
                                    for (int i = 0; i < querylist.get(0).getaFollowername().size(); i++) {
                                        if (Followername.equals(querylist.get(0).getaFollowername().get(i))) {
                                            isFollowed = true;
                                        }
                                    }
                                    if (isFollowed) {
                                        Follow follow = new Follow();
                                        follow.setsUsername(userID);
                                        follow.setaFollowername(querylist.get(0).getaFollowername());
                                        follow.deleteFollowername(querylist.get(0).getaFollowername(), Followername);
                                        follow.setaFollowerIcon(querylist.get(0).getaFollowerIcon());
                                        follow.deleteFollowerIcon(querylist.get(0).getaFollowerIcon(), follownamelist.get(0).getAvater());
                                        follow.update(querylist.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    User user = new User();
                                                    user.setUsername(userID);
                                                    user.setPassword(list.get(0).getPassword());
                                                    user.setFollow_num(list.get(0).getFollow_num() - 1);
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
                                                    User follower = new User();
                                                    follower.setUsername(Followername);
                                                    follower.setPassword(follownamelist.get(0).getPassword());
                                                    follower.setFollow_num(follownamelist.get(0).getFollow_num());
                                                    follower.setFollowed_num(follownamelist.get(0).getFollowed_num() - 1);
                                                    follower.setPost_num(follownamelist.get(0).getPost_num());
                                                    follower.setPunchin_num(follownamelist.get(0).getPunchin_num());
                                                    follower.setAvater(follownamelist.get(0).getAvater());
                                                    follower.setTarget(follownamelist.get(0).getTarget());
                                                    follower.setRun_times(follownamelist.get(0).getRun_times());
                                                    follower.setRun_distance(follownamelist.get(0).getRun_distance());
                                                    follower.update(follownamelist.get(0).getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "关注失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BmobQuery<Followed> followedBmobQuery=new BmobQuery<>();
        followedBmobQuery.addWhereEqualTo("sUsername",Followername);
        followedBmobQuery.findObjects(new FindListener<Followed>() {
            @Override
            public void done(List<Followed> list, BmobException e) {
                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                userBmobQuery.addWhereEqualTo("username",userID);
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> userList, BmobException e) {
                        boolean isFollowed = false;
                        for (int i = 0; i < list.get(0).getaFollowername().size(); i++) {
                            if (Followername.equals(list.get(0).getaFollowername().get(i))) {
                                isFollowed = true;
                            }
                        }
                        if (isFollowed) {
                            Followed followed = new Followed();
                            followed.setsUsername(Followername);
                            followed.setaFollowername(list.get(0).getaFollowername());
                            followed.deleteFollowername(list.get(0).getaFollowername(), userID);
                            followed.setaFollowerIcon(list.get(0).getaFollowerIcon());
                            followed.deleteFollowerIcon(list.get(0).getaFollowerIcon(),userList.get(0).getAvater() );
                            followed.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
