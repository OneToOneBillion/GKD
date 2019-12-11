package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

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
    private BmobRelation follow;

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

    public int getFollow_num(){
        return this.follow_num;
    }

    public void setFollow_num(int follow_num){
        this.follow_num=follow_num;
    }

    public int getFollowed_num(){
        return this.followed_num;
    }

    public void setFollowed_num(int followed_num){
        this.followed_num=followed_num;
    }

    public int getPost_num(){
        return this.post_num;
    }

    public void setPollow_num(int post_num){
        this.post_num=post_num;
    }

    public String getAvater(){
        return avater;
    }

    public void setAvater(String avater){
        this.avater=avater;
    }

    public String getTarget(){
        return target;
    }

    public void setTarget(String target){
        this.target=target;
    }

    public BmobRelation getFollow(){
        return follow;
    }

    public void setFollow(BmobRelation follow){
        this.follow=follow;
    }
}
