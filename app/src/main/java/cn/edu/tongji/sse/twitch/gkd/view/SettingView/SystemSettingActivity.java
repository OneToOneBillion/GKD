package cn.edu.tongji.sse.twitch.gkd.view.SettingView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.SettingPresenter.ISystemSettingPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;

public class SystemSettingActivity<T extends ISystemSettingPresenter> extends AppCompatActivity implements ISystemSettingView {

    private ImageButton revertBtn;
    private Button submitBtn;
    private Spinner s;

    private T mISettingPresenter;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        s=findViewById(R.id.languageSettingSpinner);

        revertBtn=findViewById(R.id.revert);
        revertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        submitBtn=findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveSystemSetting();
            }
        });

        mISettingPresenter.attachView(this);
    }

    @Override
    public void onDestory(){
        super.onDestroy();
        mISettingPresenter.detachView();
    }

    @Override
    public void saveSystemSetting(){
        ;
    }
}
