package cn.edu.tongji.sse.twitch.gkd.bean;

import cn.bmob.v3.BmobObject;

public class Run extends BmobObject {
    private String sUsername=" ";
    private int iRunTotalDistace=0;
    private long iRunTotalTime=0;
    private int iRunDistance=0;
    private long iRunTime=0;
    private String sRunPicture=" ";
    private int iAvarageVelocity=0;

    public void setsUsername(String sUsername){
        this.sUsername=sUsername;
    }

    public String getsUsername(){
        return this.sUsername;
    }

    public void setiRunTotalDistace(int iRunTotalDistace){
        this.iRunTotalDistace=iRunTotalDistace;
    }

    public int getiRunTotalDistace(){
        return this.iRunTotalDistace;
    }

    public void setiRunTotalTime(long iRunTotalTime){
        this.iRunTotalTime=iRunTotalTime;
    }

    public long getiRunTotalTime(){
        return this.iRunTotalTime;
    }

    public void setiRunDistance(int iRunDistance){
        this.iRunDistance=iRunDistance;
    }

    public int getiRunDistance(){
        return this.iRunDistance;
    }

    public void setiRunTime(long iRunTime){
        this.iRunTime=iRunTime;
    }

    public long getiRunTime(){
        return this.iRunTime;
    }

    public void setsRunPicture(String sRunPicture){
        this.sRunPicture=sRunPicture;
    }

    public String getsRunPicture(){
        return this.sRunPicture;
    }

    public void setiAvarageVelocity(int iAvarageVelocity){
        this.iAvarageVelocity=iAvarageVelocity;
    }

    public int getiAvarageVelocity(){
        return this.iAvarageVelocity;
    }
}
