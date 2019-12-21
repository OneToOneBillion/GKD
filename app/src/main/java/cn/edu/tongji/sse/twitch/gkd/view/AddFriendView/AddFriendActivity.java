package cn.edu.tongji.sse.twitch.gkd.view.AddFriendView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.AddFriendPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.IAddFriendPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendView{
    private EditText addFriend;
    private TextView followername;
    private ImageView followeravater;
    private Switch isFollowed;
    private IAddFriendPresenter iAddFriendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        iAddFriendPresenter=new AddFriendPresenterImpl(this);
        ImageButton searchFriend = findViewById(R.id.searchFriend);
        ImageButton returnPersonal = findViewById(R.id.returnPersonal);
        addFriend=findViewById(R.id.addNewFriend);
        followername=findViewById(R.id.followername);
        followeravater=findViewById(R.id.followeravater);
        isFollowed=findViewById(R.id.isFollowed);
        isFollowed.setVisibility(View.INVISIBLE);

        returnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFriendActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        searchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFollowed.setVisibility(View.VISIBLE);
                iAddFriendPresenter.searchforFriend(getUserID(),addFriend.getText().toString());
            }
        });

        isFollowed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    iAddFriendPresenter.addNewFriend(getUserID(),addFriend.getText().toString());
                }
                else {
                    iAddFriendPresenter.deleteThisFriend(getUserID(),addFriend.getText().toString());
                }
            }
        });
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    //将关注按钮设置为已关注
    public void changeSwitchChecked(){
        isFollowed.setChecked(true);
    }

    //将关注按钮设置为未关注
    public void changeSwitchUnchecked(){
        isFollowed.setChecked(false);
    }

    //设置查询到的用户名
    public void setFriendName(String username){
        followername.setText(username);
    }

    //设置查询到的头像
    public void setFriendAvater(String avater){
        Bitmap bitmap = BitmapFactory.decodeFile(avater);
        followeravater.setImageBitmap(bitmap);
    }
}

