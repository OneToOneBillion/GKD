package cn.edu.tongji.sse.twitch.gkd.view.FollowListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter.FollowListPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowListPresenter.IFollowListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.RankingListActivity;

public class FollowListActivity extends AppCompatActivity implements IFollowListView{
    private RecyclerView follow_list_recyclerView;
    private ImageButton follow_list_return;
    private Context context;
    private IFollowListPresenter iFollowListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.follow_list);

        iFollowListPresenter=new FollowListPresenterImpl(this);
        follow_list_recyclerView=findViewById(R.id.follow_list_recyclerview);
        follow_list_return=findViewById(R.id.follow_list_return);

        //展示关注的列表
        iFollowListPresenter.showFollow(getUserID(),follow_list_recyclerView,context);

        //返回个人界面
        follow_list_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowListActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }
}
