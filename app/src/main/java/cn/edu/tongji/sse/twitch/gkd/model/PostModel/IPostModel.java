package cn.edu.tongji.sse.twitch.gkd.model.PostModel;

public interface IPostModel {
    void createNewPostInModel(String postContent,String userID,String photo,OnPostListener listener);

    public interface OnPostListener{
        void postSuccess();
        void postFailed();
    }
}
