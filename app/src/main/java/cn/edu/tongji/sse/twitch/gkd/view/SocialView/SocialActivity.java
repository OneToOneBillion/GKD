package cn.edu.tongji.sse.twitch.gkd.view.SocialView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter.ISocialPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter.SocialPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.PostActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.IUserLoginView;

public class SocialActivity extends AppCompatActivity implements ISocialView{
    private TextView tMoments;
    private ImageButton personal,running,create_post,new_post;
    private RecyclerView post_list;
    private RecyclerView recyclerView;//声明RecyclerView
    private RecyclerViewAdapter adapterDome;//声明适配器
    private Context context;
    private IUserLoginView iUserLoginView;
    private ISocialPresenter iSocialPresenter;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.social_ui);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        iSocialPresenter=new SocialPresenterImpl(this);
        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        new_post=findViewById(R.id.new_post);
        recyclerView=findViewById(R.id.post_list);
        tMoments=findViewById(R.id.momentsText);

        iSocialPresenter.showPost(getUserID(),recyclerView,MyItemClickListener,context);

        //跳转到个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转到运动界面
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, RunningActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转到动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //发布新动态
        new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, PostActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")) {
            tMoments.setText(R.string.moments_en);
        }
        else{
            tMoments.setText(R.string.moments_cn);
        }

    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    /**
     * item＋item里的控件点击监听事件
     */
    private RecyclerViewAdapter.OnItemClickListener MyItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, RecyclerViewAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.post_likes:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };
}
