package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
    private int user_id;
    private String username=" ";
    private String password=" ";
    private int follow_num=0;
    private int followed_num=0;
    private int post_num=0;
    private int run_distance=0;
    private int run_times=0;
    private String avater=" ";
    private String target=" ";

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
