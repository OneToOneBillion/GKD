package cn.edu.tongji.sse.twitch.gkd.view.PostView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PostActivity extends AppCompatActivity {
    private ImageButton create_new_post,cancel_post;
    private EditText post_content,mpost_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        create_new_post=findViewById(R.id.creat_new_post);
        cancel_post=findViewById(R.id.cancel_post);
        post_content=findViewById(R.id.post_content);
        mpost_user=findViewById(R.id.post_user);

        create_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post p1=new Post();
                p1.setContent(post_content.getText().toString());
                p1.setUser_id(mpost_user.getText().toString());
                p1.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(),"发布动态成功！",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"发布动态失败："+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
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