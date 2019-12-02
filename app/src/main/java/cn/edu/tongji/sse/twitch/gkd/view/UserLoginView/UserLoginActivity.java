package cn.edu.tongji.sse.twitch.gkd.view.UserLoginView;

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

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.IUserLoginPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.UserLoginPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SignUpView.SignUpActivity;

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

    private EditText mEdtUsername, mEdtPwd;
    private Button mBtnLogin, mBtnClear,mBtnSignUp;
    private CheckBox mCbRememberPasswords, mCbAutomaticLogin;
    private ProgressBar mPbLoading;
    private String mUsername, mPassword;

    SharedPreferences accountSp;
    SharedPreferences cbSp;

    private IUserLoginPresenter mIUserLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Bmob.initialize(this, "e64de29218093006207f425439317230");
    }

    private void initViews(){
        //userInfo存储记住账号的用户名和密码信息，只有本应用有读写的权限
        accountSp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //cb存储两个CheckBox的勾选状态
        cbSp=this.getSharedPreferences("cb", Context.MODE_PRIVATE);

        mIUserLoginPresenter = new UserLoginPresenterImpl(this);

        mEdtUsername = findViewById(R.id.input_account);
        mEdtPwd = findViewById(R.id.input_password);
        mCbRememberPasswords = findViewById(R.id.rememberPasswordsBox);
        mCbAutomaticLogin = findViewById(R.id.automaticLoginBox);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnSignUp=findViewById(R.id.btn_signup);
        mPbLoading = findViewById(R.id.pb_loading);

        //设置两个勾选框初态
        mCbRememberPasswords.setChecked(cbSp.getBoolean("CbRememberPassword", false));
        mCbAutomaticLogin.setChecked(cbSp.getBoolean("CbAutomaticLogin",false));

        //自动登陆功能
        //如果上次登陆时勾选了自动登陆，则读取记住的账号信息，自动登陆
        if(mCbAutomaticLogin.isChecked()){
            readAccount();
            mIUserLoginPresenter.doLogin(mUsername, mPassword);
        }

        //如果上次登陆时只勾选了记住账号，则下次登陆时将账号信息放入输入框
        if(mCbRememberPasswords.isChecked()){
            readAccount();
            mEdtUsername.setText(mUsername);
            mEdtPwd.setText(mPassword);
        }

        //登录按钮响应
        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mUsername=getUserName();
                mPassword=getPassword();
                mIUserLoginPresenter.doLogin(mUsername, mPassword);
                //如果记住账号勾选框被勾选，则将账号信息保存到本地
                if (mCbRememberPasswords.isChecked()){
                    saveAccount(mUsername, mPassword);

                    mIUserLoginPresenter.safeAccount(mUsername, mPassword);
                }
                saveCbState();
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mIUserLoginPresenter.clear();
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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
    public void saveAccount(String un, String pwd){
        Editor editor=accountSp.edit();
        editor.putString("username", un);
        editor.putString("password", pwd);
        editor.apply();
    }

    @Override
    public void readAccount(){
        mUsername=accountSp.getString("username","");
        mPassword=accountSp.getString("password","");
    }

    @Override
    public void saveCbState(){
        Editor e=cbSp.edit();
        e.putBoolean("CbRememberPassword", mCbRememberPasswords.isChecked());
        e.putBoolean("CbAutomaticLogin", mCbAutomaticLogin.isChecked());
        e.apply();
    }
}
