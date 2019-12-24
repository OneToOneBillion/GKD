package cn.edu.tongji.sse.twitch.gkd.model.PostModel;

import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.IPostPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.PostPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.IPostView;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.PostActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class PostModelImpl implements IPostModel {
    private IPostView iPostView;
    private IPostPresenter iPostPresenter;

    public PostModelImpl(IPostPresenter IPostPresenter){
        iPostPresenter=IPostPresenter;
    }

    public void createNewPostInModel(String postContent,String userID,String photo,IPostModel.OnPostListener listener){

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        Post p1=new Post();
        p1.setContent(postContent);
        p1.setUser_id(userID);
        p1.setTime(formatter.format(date));
        p1.setPhoto(photo);
        p1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("username",userID);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            User user=new User();
                            user.setUsername(userID);
                            user.setPassword(list.get(0).getPassword());
                            user.setFollow_num(list.get(0).getFollow_num());
                            user.setFollowed_num(list.get(0).getFollowed_num());
                            user.setPost_num(list.get(0).getPost_num()+1);
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
                    listener.postSuccess();
                }
                else {
                    Toast.makeText(getApplicationContext(),"发布动态失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                    listener.postFailed();
                }
            }
        });
    }

}
