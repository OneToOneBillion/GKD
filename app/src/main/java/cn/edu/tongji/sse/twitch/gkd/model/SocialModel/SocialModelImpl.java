package cn.edu.tongji.sse.twitch.gkd.model.SocialModel;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter.ISocialPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.ISocialView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class SocialModelImpl implements ISocialModel {
    private ISocialView iSocialView;
    private ISocialPresenter iSocialPresenter;
    private RecyclerViewAdapter adapterDome;

    public SocialModelImpl(ISocialPresenter ISocialPresenter){
        iSocialPresenter=ISocialPresenter;
    }

    public void showPostList(String userID, RecyclerView recyclerView, Context context, OnShowPostListener onShowPostListener){
        BmobQuery<Post> query=new BmobQuery<>();
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> userList, BmobException e) {
                            for(int i=0;i<userList.get(0).getFollow_num();i++){
                                //关注者的动态
                            }
                            Toast.makeText(getApplicationContext(), "查询成功：" + list.size(), Toast.LENGTH_SHORT).show();
                            String[] post_avater = new String[list.size()];
                            String[] post_name = new String[list.size()];
                            String[] post_content = new String[list.size()];
                            String[] post_photo=new String[list.size()];
                            String[] post_time = new String[list.size()];
                            int[] post_likes = new int[list.size()];
                            int[] postLikesnum=new int[list.size()];
                            boolean isFollowed=false;
                            for (int i = 0; i < list.size(); i++) {
                                post_avater[i] = userList.get(0).getAvater();
                                post_name[i] = list.get(i).getUser_id();
                                post_content[i] = list.get(i).getContent();
                                post_photo[i]=list.get(i).getPhoto();
                                post_time[i] = list.get(i).getTime();
                                for(int j=0;j<list.get(i).getLikesList().size();j++){
                                    if(userID==list.get(i).getLikesList().get(j)){
                                        isFollowed=true;
                                    }
                                }
                                if(isFollowed){
                                    post_likes[i] = R.drawable.like_after;
                                }
                                else {
                                    post_likes[i] = R.drawable.like_before;
                                }
                                postLikesnum[i]=list.get(i).getLikes();
                            }
                            adapterDome = new RecyclerViewAdapter(userID,context, post_avater, post_name, post_content,post_photo, post_time, post_likes,postLikesnum);
                            LinearLayoutManager manager = new LinearLayoutManager(context);
                            manager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapterDome);
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败："+e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addPostLikes(String userID,String time,String likesname,OnShowPostListener onShowPostListener){
        BmobQuery<Post> postBmobQuery=new BmobQuery<>();
        postBmobQuery.addWhereEqualTo("user_id",userID);
        postBmobQuery.addWhereEqualTo("time",time);
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                Post post=new Post();
                post.setUser_id(userID);
                post.setPhoto(list.get(0).getPhoto());
                post.setTime(time);
                post.setContent(list.get(0).getContent());
                post.setLikes(list.get(0).getLikes());
                post.setLikesList(list.get(0).getLikesList());
                post.addLikesList(list.get(0).getLikesList(),likesname);
                post.update(list.get(0).getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }
    public void deletePostLikes(String userID,String time,String likesname,OnShowPostListener onShowPostListener){
        BmobQuery<Post> postBmobQuery=new BmobQuery<>();
        postBmobQuery.addWhereEqualTo("user_id",userID);
        postBmobQuery.addWhereEqualTo("time",time);
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                Post post=new Post();
                post.setUser_id(userID);
                post.setPhoto(list.get(0).getPhoto());
                post.setTime(time);
                post.setContent(list.get(0).getContent());
                post.setLikes(list.get(0).getLikes());
                post.setLikesList(list.get(0).getLikesList());
                post.deleteLikesList(list.get(0).getLikesList(),likesname);
                post.update(list.get(0).getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }
}
