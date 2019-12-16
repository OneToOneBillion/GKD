package cn.edu.tongji.sse.twitch.gkd.view.RunningDataListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningDataListPresenter.IRunningDataListPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningDataListPresenter.RunningDataListPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.IRankingListView;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.RankingListActivity;

public class RunningDataListActivity extends AppCompatActivity implements IRunningDataListView {
    private RecyclerView running_data_list_recyclerview;
    private ImageButton running_data_list_return;
    private Context context;
    private IRunningDataListPresenter iRunningDataListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.running_data_list);

        iRunningDataListPresenter=new RunningDataListPresenterImpl(this);
        running_data_list_recyclerview=findViewById(R.id.running_data_list_recyclerview);
        running_data_list_return=findViewById(R.id.running_data_list_return);

        iRunningDataListPresenter.showRunningData(getUserID(),running_data_list_recyclerview,context);
        running_data_list_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningDataListActivity.this, PersonalActivity.class);
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
