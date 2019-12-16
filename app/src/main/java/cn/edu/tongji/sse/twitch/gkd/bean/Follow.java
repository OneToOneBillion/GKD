package cn.edu.tongji.sse.twitch.gkd.bean;

import android.widget.Toast;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class Follow extends BmobObject {
    private String sUsername;
    private ArrayList<String> aFollowername=new ArrayList<>();

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public ArrayList<String> getaFollowername() {
        return aFollowername;
    }

    public void addaFollowername(String sFollowername) {
        this.aFollowername.add(sFollowername);
    }

    public void deleteFollowername(String sFollowername){
        this.aFollowername.remove(sFollowername);
    }
}
