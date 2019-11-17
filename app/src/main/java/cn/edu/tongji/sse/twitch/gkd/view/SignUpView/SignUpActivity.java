package cn.edu.tongji.sse.twitch.gkd.view.SignUpView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.UserLoginActivity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class SignUpActivity extends AppCompatActivity implements ISignUpView{
    private EditText input_signup_username,input_signup_password,input_signup_password_again;
    private Button sign_up,cancel;
    private ISignUpView signUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        input_signup_username=findViewById(R.id.input_signup_username);
        input_signup_password=findViewById(R.id.input_signup_password);
        input_signup_password_again=findViewById(R.id.input_signup_password_again);
        sign_up=findViewById(R.id.sign_up);
        cancel=findViewById(R.id.cancel_sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_signup_password.getText().toString().equals(input_signup_password_again.getText().toString()))
                {
                    //Toast.makeText(getApplicationContext(),"创建用户成功！",Toast.LENGTH_SHORT).show();
                    //String new_username=signUpView.getUserName();
                    //String new_password=signUpView.getPassword();
                    User u1=new User();
                    u1.setUsername(input_signup_username.getText().toString());
                    u1.setPassword(input_signup_password.getText().toString());
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
                    //CreateAccountPresenter.createAccount(input_signup_username.getText().toString(),input_signup_password.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(),"两次输入密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getUserName(){
        return input_signup_username.getText().toString();
    }

    public String getPassword(){
        return input_signup_password.getText().toString();
    }
}
