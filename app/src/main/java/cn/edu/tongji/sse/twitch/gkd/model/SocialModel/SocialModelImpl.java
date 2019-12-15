package cn.edu.tongji.sse.twitch.gkd.model.SocialModel;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
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
        query.addWhereEqualTo("user_id", userID);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "查询成功："+list.size() , Toast.LENGTH_SHORT).show();
                    int[] post_avater=new int[list.size()];
                    String[] post_name=new String[list.size()];
                    String[] post_content=new String[list.size()];
                    String[] post_time=new String[list.size()];
                    int[] post_likes=new int[list.size()];
                    for (int i=0;i<list.size();i++){
                        post_avater[i]= R.drawable.timg;
                        post_name[i]="张三";
                        post_content[i]="今天又跑了50米，踏实。";
                        post_time[i]="2019/12/8 19:28";
                        post_likes[i]=R.drawable.hhh;
                    }
                    adapterDome = new RecyclerViewAdapter(context,post_avater,post_name,post_content,post_time,post_likes);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapterDome);
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败："+e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
