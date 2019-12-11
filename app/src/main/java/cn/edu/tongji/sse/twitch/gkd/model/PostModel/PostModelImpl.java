package cn.edu.tongji.sse.twitch.gkd.model.PostModel;

import android.content.Intent;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
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

    public void createNewPostInModel(String postContent,String userID,IPostModel.OnPostListener listener){
        Post p1=new Post();
        p1.setContent(postContent);
        p1.setUser_id(userID);
        p1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"发布动态成功！",Toast.LENGTH_LONG).show();
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
