package cn.edu.tongji.sse.twitch.gkd.view.RankingListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.RankingListPresenter.IRankingListPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RankingListPresenter.RankingListPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.PostActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class RankingListActivity extends AppCompatActivity implements IRankingListView {
    private RecyclerView ranking_list_recyclerview;
    private ImageButton ranking_list_return;
    private IRankingListPresenter iRankingListPresenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.ranking_list);

        iRankingListPresenter=new RankingListPresenterImpl(this);
        ranking_list_recyclerview=findViewById(R.id.ranking_list_recyclerview);
        ranking_list_return=findViewById(R.id.ranking_list_return);

        //添加适配器，展示排行榜
        iRankingListPresenter.showRanking(getUserID(),ranking_list_recyclerview,context);

        //返回个人界面
        ranking_list_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingListActivity.this, PersonalActivity.class);
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
