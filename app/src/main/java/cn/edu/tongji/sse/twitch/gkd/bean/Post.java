package cn.edu.tongji.sse.twitch.gkd.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject {
    private String user_id=" ";
    private String content=" ";
    private int likes=0;
    private String time="";
    private String photo="";
    private ArrayList<String> likesList=new ArrayList<>();

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

    public void setLikes(int likes){
        this.likes=likes;
    }

    public int getLikes(){
        return this.likes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<String> getLikesList() {
        return likesList;
    }

    public void setLikesList(ArrayList<String> likesList) {
        this.likesList = likesList;
    }

    public void addLikesList(ArrayList<String> likesList,String username){
        likesList.add(username);
    }

    public void deleteLikesList(ArrayList<String> likesList,String username){
        for(int i=0;i<likesList.size();i++){
            String s=likesList.get(i);
            if(s.equals(username)){
                likesList.remove(s);
            }
        }
    }
}
