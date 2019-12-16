package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;

public class Run extends BmobObject {
    private String sUsername="";
    private double sRunDistance=0;
    private long iRunTime=0;
    private String time="";

    public void setsUsername(String sUsername){
        this.sUsername=sUsername;
    }

    public String getsUsername(){
        return this.sUsername;
    }

    public void setsRunDistance(double sRunDistance) {
        this.sRunDistance = sRunDistance;
    }

    public double getsRunDistance() {
        return sRunDistance;
    }

    public void setiRunTime(long iRunTime){
        this.iRunTime=iRunTime;
    }

    public long getiRunTime(){
        return this.iRunTime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
