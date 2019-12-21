package cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.ISocialModel;
import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.SocialModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.ISocialView;

public class SocialPresenterImpl implements ISocialPresenter, ISocialModel.OnShowPostListener {
    private ISocialView iSocialView;
    private ISocialModel iSocialModel;
    private RecyclerViewAdapter recyclerViewAdapter;

    public SocialPresenterImpl(ISocialView ISocialView){
        iSocialView=ISocialView;
        iSocialModel=new SocialModelImpl(this);
    }

    public SocialPresenterImpl(RecyclerViewAdapter RecyclerViewAdapter){
        recyclerViewAdapter=RecyclerViewAdapter;
        iSocialModel=new SocialModelImpl(this);
    }

    public void showPost(String userID, RecyclerView recyclerView, RecyclerViewAdapter.OnItemClickListener MyItemClickListener,Context context){
        iSocialModel.showPostList(userID,recyclerView,MyItemClickListener,context,this);
    }

    public void addPostLikes(String userID,String time,String likesname){
        iSocialModel.addPostLikes(userID,time,likesname,this);
    }

    public void deletePostLikes(String userID,String time,String likesname){
        iSocialModel.deletePostLikes(userID,time,likesname,this);
    }

    public void showPostSuccess(){

    }
    public void showPostFailed(){

    }
}
