package cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.PostModel.IPostModel;
import cn.edu.tongji.sse.twitch.gkd.model.PostModel.PostModelImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.IPostView;

public class PostPresenterImpl implements IPostPresenter,IPostModel.OnPostListener {
    private IPostModel iPostModel;
    private IPostView iPostView;

    public PostPresenterImpl(IPostView IPostView){
        iPostView =IPostView;
        iPostModel=new PostModelImpl(this);
    }

    public void createNewPost(String postContent,String userID){
        iPostModel.createNewPostInModel(postContent,userID,this);
    }

    public String getUserID(){
        return iPostView.getUserID();
    }
    public String getPostContent(){
        return iPostView.getPostContent();
    }
    public void postSuccess(){

    }
    public void postFailed(){

    }
}
