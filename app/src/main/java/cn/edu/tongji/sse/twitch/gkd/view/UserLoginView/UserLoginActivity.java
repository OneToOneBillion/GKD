package cn.edu.tongji.sse.twitch.gkd.view.UserLoginView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.IUserLoginPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.UserLoginPresenter.UserLoginPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SignUpView.SignUpActivity;

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

    private ImageView mHeadPortrait;
    private EditText mEdtUsername, mEdtPwd;
    private Button mBtnLogin, mBtnSignUp;
    private CheckBox mCbRememberPasswords, mCbAutomaticLogin;
    private String mUsername, mPassword;
    private TextView mWarning;

    SharedPreferences accountSp;
    SharedPreferences cbSp;
    SharedPreferences sysSettingSp;

    private IUserLoginPresenter mIUserLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        initViews();
    }

    private void initViews(){
        //初始化Bmob数据库
        Bmob.initialize(this, "e64de29218093006207f425439317230");

        //userInfo存储记住账号的用户名和密码信息，只有本应用有读写的权限
        accountSp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //cb存储两个CheckBox的勾选状态，只有本应用有读写的权限
        cbSp=this.getSharedPreferences("cb", Context.MODE_PRIVATE);
        //读取系统设置的SharedPreferences
        sysSettingSp=this.getSharedPreferences("sysSetting",Context.MODE_PRIVATE);

        mIUserLoginPresenter = new UserLoginPresenterImpl(this);

        //头像框
        mHeadPortrait=findViewById(R.id.head_portrait_img);
        BmobQuery<User> userBmobQuery=new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("username",mUsername);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                Bitmap bitmap = BitmapFactory.decodeFile(list.get(0).getAvater());
                mHeadPortrait.setImageBitmap(bitmap);
            }
        });

        mWarning=findViewById(R.id.warningText);
        mWarning.setVisibility(View.INVISIBLE);

        mEdtUsername = findViewById(R.id.input_account);
        mEdtPwd = findViewById(R.id.input_password);

        mCbRememberPasswords = findViewById(R.id.rememberPasswordsBox);
        mCbAutomaticLogin = findViewById(R.id.automaticLoginBox);
        //设置两个勾选框初态
        mCbRememberPasswords.setChecked(readSaveCbRPState());
        mCbAutomaticLogin.setChecked(readSaveCbALState());
        //自动登陆功能
        //如果上次登陆时勾选了自动登陆，则读取记住的账号信息，自动登陆
        if(mCbAutomaticLogin.isChecked()){
            mUsername=readSaveUn();
            mPassword=readSavePwd();
            mIUserLoginPresenter.doLogin(mUsername, mPassword);
        }
        //如果上次登陆时只勾选了记住账号，则下次登陆时将账号信息放入输入框
        if(mCbRememberPasswords.isChecked()){
            mUsername=readSaveUn();
            mPassword=readSavePwd();
            mEdtUsername.setText(mUsername);
            mEdtPwd.setText(mPassword);
        }

        //登录按钮
        mBtnLogin = findViewById(R.id.btn_login);
        //设置登录按钮响应事件
        //(!!!逻辑需要重写)
        //(!!!让doLogin返回Boolean值，当登陆成功时执行toRunningActivity和保存页面信息一系列活动)
        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mUsername=getUserName();
                mPassword=getPassword();
                mIUserLoginPresenter.doLogin(mUsername, mPassword);
                //如果记住账号勾选框被勾选，则将账号信息保存到本地
                if (mCbRememberPasswords.isChecked()){
                    saveAccount(mUsername, mPassword);
                }
                saveCbState(getCbRPState(), getCbALState());
            }
        });

        //注册按钮
        mBtnSignUp=findViewById(R.id.btn_signup);
        //设置注册按钮响应事件
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignUpActivity();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")) {
            mBtnLogin.setText(R.string.login_en);
            mBtnSignUp.setText(R.string.sign_up_en);
            mCbRememberPasswords.setText(R.string.remember_password_en);
            mCbAutomaticLogin.setText(R.string.auto_login_en);
            mEdtUsername.setHint(R.string.account_en);
            mEdtPwd.setHint(R.string.password_en);
            mWarning.setText(R.string.warning_en);
        }
        else{
            mBtnLogin.setText(R.string.login_cn);
            mBtnSignUp.setText(R.string.sign_up_cn);
            mCbRememberPasswords.setText(R.string.remember_password_cn);
            mCbAutomaticLogin.setText(R.string.auto_login_cn);
            mEdtUsername.setHint(R.string.account_cn);
            mEdtPwd.setHint(R.string.password_cn);
            mWarning.setText(R.string.warning_cn);
        }
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
    public Boolean getCbRPState(){
        return mCbRememberPasswords.isChecked();
    }

    @Override
    public Boolean getCbALState(){
        return mCbAutomaticLogin.isChecked();
    }

    @Override
    public void toRunningActivity(){
        Toast.makeText(this,"Login success, to RunningActivity",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserLoginActivity.this, RunningActivity.class);
        intent.putExtra("data",getUserName());
        startActivity(intent);
        finish();
    }

    @Override
    public void toSignUpActivity(){
        Intent intent = new Intent(UserLoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError(){
        mWarning.setVisibility(View.VISIBLE);
        mEdtUsername.setBackgroundResource(R.drawable.input_warning);
        mEdtPwd.setBackgroundResource(R.drawable.input_warning);
    }

    @Override
    public void saveAccount(String un, String pwd){
        Editor editor=accountSp.edit();
        editor.putString("username", un);
        editor.putString("password", pwd);
        editor.apply();
    }

    @Override
    public String readSaveUn(){
        return accountSp.getString("username","");
    }

    public String readSavePwd(){
        return accountSp.getString("password","");
    }

    @Override
    public void saveCbState(Boolean cbRPState, Boolean cbALState){
        Editor editor=cbSp.edit();
        editor.putBoolean("CbRememberPassword", cbRPState);
        editor.putBoolean("CbAutomaticLogin", cbALState);
        editor.apply();
    }

    @Override
    public Boolean readSaveCbRPState(){
        return cbSp.getBoolean("CbRememberPassword",false);
    }

    @Override
    public Boolean readSaveCbALState(){
        return cbSp.getBoolean("CbAutomaticLogin", false);
    }
}
