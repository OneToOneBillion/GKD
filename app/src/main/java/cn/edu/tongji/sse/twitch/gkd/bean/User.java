package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobObject {
    private String username=" ";
    private String password=" ";
    private int follow_num=0;
    private int followed_num=0;
    private int post_num=0;
    private int punchin_num=0;
    private double run_distance=0;
    private long run_times=0;
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

    public void setPost_num(int post_num){
        this.post_num=post_num;
    }

    public int getPunchin_num() {
        return punchin_num;
    }

    public void setPunchin_num(int punchin_num) {
        this.punchin_num = punchin_num;
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

    public double getRun_distance() {
        return run_distance;
    }

    public void setRun_distance(double run_distance) {
        this.run_distance = run_distance;
    }

    public long getRun_times() {
        return run_times;
    }

    public void setRun_times(long run_times) {
        this.run_times = run_times;
    }
}
