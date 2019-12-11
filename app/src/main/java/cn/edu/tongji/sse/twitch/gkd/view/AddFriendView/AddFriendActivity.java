package cn.edu.tongji.sse.twitch.gkd.view.AddFriendView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.AddFriendPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.AddFriendPresenter.IAddFriendPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendView{
    private ImageButton searchFriend,returnPersonal;
    private EditText addFriend;
    private TextView followername;
    private Switch isFollowed;
    private IAddFriendPresenter iAddFriendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        iAddFriendPresenter=new AddFriendPresenterImpl(this);
        searchFriend=findViewById(R.id.searchFriend);
        returnPersonal=findViewById(R.id.returnPersonal);
        addFriend=findViewById(R.id.addNewFriend);
        followername=findViewById(R.id.followername);
        isFollowed=findViewById(R.id.isFollowed);

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
                followername.setText(addFriend.getText().toString());
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
}

