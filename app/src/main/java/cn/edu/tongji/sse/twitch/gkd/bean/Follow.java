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
    private ArrayList<String> aFollowerIcon=new ArrayList<>();

    public Follow(){

    }

    public Follow(String Username){
        aFollowername.add(Username);
        aFollowerIcon.add(" ");
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

    public void addaFollowername(ArrayList<String> aFollowername, String sFollowername) {
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

    public ArrayList<String> getaFollowerIcon() {
        return aFollowerIcon;
    }

    public void setaFollowerIcon(ArrayList<String> aFollowerIcon) {
        this.aFollowerIcon = aFollowerIcon;
    }

    public void addaFollowerIcon(ArrayList<String> aFollowerIcon, String sFollowerIcon) {
        aFollowerIcon.add(sFollowerIcon);
    }

    public void deleteFollowerIcon(ArrayList<String> aFollowerIcon,String sFollowerIcon){
        for(int i=0;i<aFollowerIcon.size();i++){
            String s=aFollowerIcon.get(i);
            if(s.equals(sFollowerIcon)){
                aFollowerIcon.remove(s);
            }
        }
    }
}
