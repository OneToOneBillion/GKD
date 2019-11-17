package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject {
    private String user_id=" ";
    private String content=" ";
    private int likes=0;

    public void setUser_id(String user_id){
        this.user_id=user_id;
    }

    public String getUser_id(){
        return this.user_id;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getContent(){
        return this.content;
    }

    public void setLikes(){
        this.likes=likes;
    }

    public int getLikes(){
        return this.likes;
    }
}
