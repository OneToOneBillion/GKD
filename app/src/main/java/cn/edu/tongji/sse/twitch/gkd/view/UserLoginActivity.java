package cn.edu.tongji.sse.twitch.gkd.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.IUserLoginPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenterImpl;

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

    private EditText mEdtUsername, mEdtPwd;
    private Button mBtnLogin, mBtnClear;
    private CheckBox mCbRememberPasswords, mCbAutomaticLogin;
    private ProgressBar mPbLoading;
    private String username,password;

    SharedPreferences sp;

    private IUserLoginPresenter mIUserLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        //userInfo存储记住账号的用户名和密码信息，只有本应用有读写的权限
        sp=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mIUserLoginPresenter = new UserLoginPresenterImpl(this);

        mEdtUsername = findViewById(R.id.input_account);
        mEdtPwd = findViewById(R.id.input_password);
        mCbRememberPasswords = findViewById(R.id.rememberPasswordsBox);
        mCbAutomaticLogin = findViewById(R.id.automaticLoginBox);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnLogin = findViewById(R.id.btn_login);
        mPbLoading = findViewById(R.id.pb_loading);

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mIUserLoginPresenter.doLogin();
                //如果记住账号勾选框被勾选，则将账号信息保存到本地
                if (mCbRememberPasswords.isChecked()){

                }

            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mIUserLoginPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName(){
        return mEdtUsername.getText().toString();
    }

    @Override
    public String getPassword(){
        return mEdtPwd.getText().toString();
    }

    @Override
    public void clearUserName(){
        mEdtUsername.setText("");
    }

    @Override
    public void clearPassword(){
        mEdtPwd.setText("");
    }

    @Override
    public void showLoading(){
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading(){
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(){
        Toast.makeText(this,"Login success, to MainActivity",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserLoginActivity.this, RunningActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFailedError(){
        Toast.makeText(this,"Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAccount(){
        Editor editor=sp.edit();
        username=getUserName();
        password=getPassword();
        editor.putString(username, username);
        editor.putString(password, password);
        editor.apply();
    }

    @Override
    public void readAccount(){
        username=sp.getString("username","");
        password=sp.getString("password","");
    }
}
