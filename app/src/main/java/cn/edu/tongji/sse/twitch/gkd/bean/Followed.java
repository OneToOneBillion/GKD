package cn.edu.tongji.sse.twitch.gkd.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Followed extends BmobObject {
    private String sUsername;
    private ArrayList<String> aFollowername=new ArrayList<>();

    public Followed(){

    }

    public Followed(String Username){
        aFollowername.add(Username);
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public ArrayList<String> getaFollowername() {
        return aFollowername;
    }

    public void setaFollowername(ArrayList<String> aFollowername) {
        this.aFollowername = aFollowername;
    }

    public void addaFollowername(ArrayList<String> aFollowername,String sFollowername) {
        aFollowername.add(sFollowername);
    }

    public void deleteFollowername(ArrayList<String> aFollowername,String sFollowername){
        for(int i=0;i<aFollowername.size();i++){
            String s=aFollowername.get(i);
            if(s.equals(sFollowername)){
                aFollowername.remove(s);
            }
        }
    }
}
