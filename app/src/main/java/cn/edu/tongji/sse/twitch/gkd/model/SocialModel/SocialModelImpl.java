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
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
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

    public void showPostList(String userID, RecyclerView recyclerView, RecyclerViewAdapter.OnItemClickListener MyItemClickListener,Context context, OnShowPostListener onShowPostListener){
        BmobQuery<Post> postBmobQuery=new BmobQuery<>();
        postBmobQuery.order("-createdAt");
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    BmobQuery<Follow> followBmobQuery=new BmobQuery<>();
                    followBmobQuery.addWhereEqualTo("sUsername",userID);
                    followBmobQuery.findObjects(new FindListener<Follow>() {
                        @Override
                        public void done(List<Follow> followList, BmobException e) {
                            int m=0;
                            String[] post_avater = new String[list.size()];
                            String[] post_name = new String[list.size()];
                            String[] post_content = new String[list.size()];
                            String[] post_photo=new String[list.size()];
                            String[] post_time = new String[list.size()];
                            int[] post_likes = new int[list.size()];
                            int[] postLikesnum=new int[list.size()];
                            boolean isFollowed=false;
                            for(int i=0;i<list.size();i++){
                                for(int j=0;j<followList.get(0).getaFollowername().size();j++){
                                    if(list.get(i).getUser_id().equals(followList.get(0).getaFollowername().get(j))){
                                        post_avater[m] = followList.get(0).getaFollowerIcon().get(j);
                                        post_name[m] = list.get(i).getUser_id();
                                        post_content[m] = list.get(i).getContent();
                                        post_photo[m]=list.get(i).getPhoto();
                                        post_time[m] = list.get(i).getTime();
                                        for(int n=0;n<list.get(i).getLikesList().size();n++){
                                            if(userID.equals(list.get(i).getLikesList().get(n))){
                                                isFollowed=true;
                                            }
                                        }
                                        if(isFollowed){
                                            post_likes[m] = R.drawable.like_after;
                                        }
                                        else {
                                            post_likes[m] = R.drawable.like_before;
                                        }
                                        postLikesnum[m]=list.get(i).getLikes();
                                        m=m+1;
                                    }
                                }
                            }
                            String[] spost_avater = new String[m];
                            String[] spost_name = new String[m];
                            String[] spost_content = new String[m];
                            String[] spost_photo=new String[m];
                            String[] spost_time = new String[m];
                            int[] spost_likes = new int[m];
                            int[] spostLikesnum=new int[m];
                            System.arraycopy(post_avater,0,spost_avater,0,m);
                            System.arraycopy(post_name,0,spost_name,0,m);
                            System.arraycopy(post_content,0,spost_content,0,m);
                            System.arraycopy(post_photo,0,spost_photo,0,m);
                            System.arraycopy(post_time,0,spost_time,0,m);
                            System.arraycopy(post_likes,0,spost_likes,0,m);
                            System.arraycopy(postLikesnum,0,spostLikesnum,0,m);
                            adapterDome = new RecyclerViewAdapter(userID,context, spost_avater, spost_name, spost_content,spost_photo, spost_time, spost_likes,spostLikesnum);
                            LinearLayoutManager manager = new LinearLayoutManager(context);
                            manager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapterDome);
                            // 设置item及item中控件的点击事件
                            adapterDome.setOnItemClickListener(MyItemClickListener);
                        }
                    });
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
                post.setLikes(list.get(0).getLikes()+1);
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
                post.setLikes(list.get(0).getLikes()-1);
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
