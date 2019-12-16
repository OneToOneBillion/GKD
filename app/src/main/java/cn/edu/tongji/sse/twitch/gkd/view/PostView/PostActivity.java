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
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.IPostPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.PostPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PostActivity extends AppCompatActivity implements IPostView {
    private ImageButton cancel_post;
    private Button create_new_post;
    private TextView tCreatePost;
    private EditText post_content;
    private IPostPresenter iPostPresenter;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        iPostPresenter=new PostPresenterImpl(this);
        create_new_post=findViewById(R.id.creat_new_post);
        cancel_post=findViewById(R.id.cancel_post);
        post_content=findViewById(R.id.post_content);
        tCreatePost=findViewById(R.id.createPostText);

        create_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPostPresenter.createNewPost(post_content.getText().toString(),getUserID());
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"取消发布动态！",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
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

    /*public String getUserID(){
        return mpost_user.getText().toString();
    }*/

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    public String getPostContent(){
        return post_content.getText().toString();
    }
}