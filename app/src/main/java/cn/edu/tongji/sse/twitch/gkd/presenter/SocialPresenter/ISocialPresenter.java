package cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.model.SocialModel.ISocialModel;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;

public interface ISocialPresenter {
    void showPost(String userID, RecyclerView recyclerView, RecyclerViewAdapter.OnItemClickListener MyItemClickListener, Context context);
    void addPostLikes(String userID,String time,String likesname);
    void deletePostLikes(String userID,String time,String likesname);
}
