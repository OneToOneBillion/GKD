package cn.edu.tongji.sse.twitch.gkd.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;

public class SocialActivity extends AppCompatActivity {
    private ImageButton personal,running,create_post,new_post;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_ui);

        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        new_post=findViewById(R.id.new_post);

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
