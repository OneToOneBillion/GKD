package cn.edu.tongji.sse.twitch.gkd.view.SystemSettingView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;

public class SystemSettingActivity extends AppCompatActivity implements ISystemSettingView {

    private ImageButton revertBtn;
    private Button submitBtn;
    private Spinner languageSettingSpinner;
    private TextView tLanguage, tSysSetting;
    private String mLanguageSetting;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_setting);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        languageSettingSpinner=findViewById(R.id.languageSettingSpinner);
        tLanguage=findViewById(R.id.languageSettingText);
        tSysSetting=findViewById(R.id.settingText);

        revertBtn=findViewById(R.id.revert);
        //设置返回按钮响应事件
        revertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPersonalView();
            }
        });

        //设置提交按钮响应事件
        //将两项设置的值保存到本地文件
        //并实现页面自我刷新
        submitBtn=findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mLanguageSetting=getLanguageSetting();
                saveSystemSetting(mLanguageSetting);
                refreshSysSettingView();
            }
        });

        //语言设置及设置Spinner默认选中的值
        if(sysSettingSp.getString("language","").equals("English")){
            tLanguage.setText(R.string.language_en);
            languageSettingSpinner.setSelection(0, true);
            submitBtn.setText(R.string.submit_en);
            tSysSetting.setText(R.string.sys_setting_en);
        }
        else{
            tLanguage.setText(R.string.language_cn);
            languageSettingSpinner.setSelection(1, true);
            submitBtn.setText(R.string.submit_cn);
            tSysSetting.setText(R.string.sys_setting_cn);
        }
    }

    @Override
    public void saveSystemSetting(String l){
        Editor editor=sysSettingSp.edit();
        editor.putString("language", l);
        editor.apply();
    }

    @Override
    public String getLanguageSetting(){
        return languageSettingSpinner.getSelectedItem().toString();
    }

    @Override
    public void toPersonalView(){
        Intent intent = new Intent(SystemSettingActivity.this, PersonalActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        finish();
    }

    @Override
    public void refreshSysSettingView(){
        finish();
        Intent intent = new Intent(SystemSettingActivity.this,SystemSettingActivity.class);
        startActivity(intent);
    }
}
