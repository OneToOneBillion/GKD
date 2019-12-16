package cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.ISocialModel;
import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.SocialModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.ISocialView;

public class SocialPresenterImpl implements ISocialPresenter, ISocialModel.OnShowPostListener {
    private ISocialView iSocialView;
    private ISocialModel iSocialModel;

    public SocialPresenterImpl(ISocialView ISocialView){
        iSocialView=ISocialView;
        iSocialModel=new SocialModelImpl(this);
    }

    public void showPost(String userID, RecyclerView recyclerView, Context context){
        iSocialModel.showPostList(userID,recyclerView,context,this);
    }

    public void showPostSuccess(){

    }
    public void showPostFailed(){

    }
}
