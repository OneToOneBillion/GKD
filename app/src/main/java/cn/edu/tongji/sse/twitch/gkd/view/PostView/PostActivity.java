package cn.edu.tongji.sse.twitch.gkd.view.PostView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PostActivity extends AppCompatActivity {
    private ImageButton cancel_post;
    private Button create_new_post;
    private TextView tCreatePost;
    private EditText post_content,mpost_user;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        create_new_post=findViewById(R.id.creat_new_post);
        cancel_post=findViewById(R.id.cancel_post);
        post_content=findViewById(R.id.post_content);
        mpost_user=findViewById(R.id.post_user);
        tCreatePost=findViewById(R.id.createPostText);

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

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")) {
            tCreatePost.setText(R.string.create_post_en);
            create_new_post.setText(R.string.create_en);
        }
        else{
            tCreatePost.setText(R.string.create_post_cn);
            create_new_post.setText(R.string.create_cn);
        }
    }
}