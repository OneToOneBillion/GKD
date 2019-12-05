package cn.edu.tongji.sse.twitch.gkd.view.SignUpView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.UserLoginActivity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class SignUpActivity extends AppCompatActivity implements ISignUpView{
    private TextView tSignUp, tUnExistWarning, tPwdInconsistentWarning;
    private EditText input_sign_up_username,input_sign_up_password,input_sign_up_password_again;
    private Button btn_sign_up,btn_cancel;
    private String mUserName, mPassword;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        sysSettingSp=getSharedPreferences("sysSetting",MODE_PRIVATE);

        tSignUp=findViewById(R.id.signUpText);
        tUnExistWarning=findViewById(R.id.un_exist_warning);
        tPwdInconsistentWarning=findViewById(R.id.pwd_inconsistent_warning);
        input_sign_up_username=findViewById(R.id.input_sign_up_username);
        input_sign_up_password=findViewById(R.id.input_sign_up_password);
        input_sign_up_password_again=findViewById(R.id.input_sign_up_password_again);
        btn_sign_up=findViewById(R.id.sign_up);
        btn_cancel=findViewById(R.id.cancel_sign_up);

        tUnExistWarning.setVisibility(View.INVISIBLE);
        tPwdInconsistentWarning.setVisibility(View.INVISIBLE);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName=getUserName();
                mPassword=getPassword();

                //(!!!添加逻辑)
                //(!!!判断用户名是否已经存在)
                //(!!!如果存在，则无法注册，显示用户名已存在的警告信息)
                if(1+1!=2){
                    tUnExistWarning.setVisibility(View.VISIBLE);
                    input_sign_up_username.setBackgroundResource(R.drawable.input_warning);
                }
                //如果用户名合法，则判断两次输入的密码是否一致
                //如果一致，则注册成功,向数据库中添加数据，并跳转至登陆界面
                //若不一致，则无法注册，显示密码不一致的警告信息
                else {
                    if (input_sign_up_password.getText().toString().equals(input_sign_up_password_again.getText().toString())) {
                        User u1 = new User();
                        u1.setUsername(mUserName);
                        u1.setPassword(mPassword);
                        u1.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Intent intent = new Intent(SignUpActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        tPwdInconsistentWarning.setVisibility(View.VISIBLE);
                        input_sign_up_password.setBackgroundResource(R.drawable.input_warning);
                        input_sign_up_password_again.setBackgroundResource(R.drawable.input_warning);
                    }
                }
            }
        });

        //设置取消按钮响应事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, UserLoginActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")){
            tSignUp.setText(R.string.sign_up_en);
            input_sign_up_username.setHint(R.string.input_username_en);
            input_sign_up_password.setHint(R.string.input_password_en);
            input_sign_up_password_again.setHint(R.string.input_password_again_en);
            btn_sign_up.setText(R.string.sign_up_en);
            btn_cancel.setText(R.string.cancel_en);
            tUnExistWarning.setText(R.string.un_exist_warning_en);
            tPwdInconsistentWarning.setText(R.string.pwd_inconsistent_warning_en);
        }
        else{
            tSignUp.setText(R.string.sign_up_cn);
            input_sign_up_username.setHint(R.string.input_username_cn);
            input_sign_up_password.setHint(R.string.input_password_cn);
            input_sign_up_password_again.setHint(R.string.input_password_again_cn);
            btn_sign_up.setText(R.string.sign_up_cn);
            btn_cancel.setText(R.string.cancel_cn);
            tUnExistWarning.setText(R.string.un_exist_warning_cn);
            tPwdInconsistentWarning.setText(R.string.pwd_inconsistent_warning_cn);
        }
    }

    public String getUserName(){
        return input_sign_up_username.getText().toString();
    }

    public String getPassword(){
        return input_sign_up_password.getText().toString();
    }
}
