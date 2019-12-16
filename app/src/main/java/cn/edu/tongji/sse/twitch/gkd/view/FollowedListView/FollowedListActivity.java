package cn.edu.tongji.sse.twitch.gkd.view.FollowedListView;

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
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter.FollowedListPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.FollowedListPresenter.IFollowedListPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.FollowListActivity;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.IFollowListView;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;

public class FollowedListActivity extends AppCompatActivity implements IFollowedListView {
    private RecyclerView followed_list_recyclerView;
    private ImageButton followed_list_return;
    private Context context;
    private IFollowedListPresenter iFollowedListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.followed_list);

        iFollowedListPresenter = new FollowedListPresenterImpl(this);
        followed_list_recyclerView = findViewById(R.id.followed_list_recyclerview);
        followed_list_return = findViewById(R.id.followed_list_return);

        //展示关注的列表
        iFollowedListPresenter.showFollowed(getUserID(), followed_list_recyclerView, context);

        //返回个人界面
        followed_list_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowedListActivity.this, PersonalActivity.class);
                intent.putExtra("data", getUserID());
                startActivity(intent);
                finish();
            }
        });
    }

    public String getUserID() {
        Intent intent = getIntent();
        return intent.getStringExtra("data");
    }
}
