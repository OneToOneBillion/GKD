package cn.edu.tongji.sse.twitch.gkd.view.SocialView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.PostView.PostActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;

public class SocialActivity extends AppCompatActivity {
    private ImageButton personal,running,create_post,new_post;
    private RecyclerView post_list;
    private RecyclerView recyclerView;//声明RecyclerView
    private RecyclerViewAdapter adapterDome;//声明适配器
    private Context context;
    private List<String> show_post_list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.social_ui);

        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        new_post=findViewById(R.id.new_post);
        recyclerView=findViewById(R.id.post_list);

        show_post_list = new ArrayList<>();
        BmobQuery<Post> query=new BmobQuery<>();
        query.addWhereEqualTo("user_id","twitch");
        query.count(Post.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), integer+"条动态" , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败："+e.getMessage() , Toast.LENGTH_SHORT).show();
                }
                int post_num=integer;
            }
        });
        for (int i=0;i<8;i++){
            show_post_list.add("这是第"+i+"个动态");
        }
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "查询成功："+list.size() , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败："+e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });


        //
        adapterDome = new RecyclerViewAdapter(context,show_post_list);
        /*
        与ListView效果对应的可以通过LinearLayoutManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
        //LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        //GridLayoutManager manager1 = new GridLayoutManager(context,2);
        //manager1.setOrientation(GridLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterDome);

        //跳转到个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        //跳转到运动界面
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, RunningActivity.class);
                startActivity(intent);
            }
        });

        //跳转到动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, SocialActivity.class);
                startActivity(intent);
            }
        });

        //发布新动态
        new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
    }
}
