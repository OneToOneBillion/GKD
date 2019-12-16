package cn.edu.tongji.sse.twitch.gkd.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Followed extends BmobObject {
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
