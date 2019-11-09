package cn.edu.tongji.sse.twitch.gkd.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;

public class PostActivity extends AppCompatActivity {
    private ImageButton create_new_post,cancel_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        create_new_post=findViewById(R.id.creat_new_post);
        cancel_post=findViewById(R.id.cancel_post);

        create_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"发布动态成功！",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                startActivity(intent);
            }
        });

        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"取消发布动态！",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                startActivity(intent);
            }
        });
    }
}