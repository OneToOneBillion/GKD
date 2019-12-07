package cn.edu.tongji.sse.twitch.gkd.bean;

/*
运动信息
运动距离、运动时长、用户id
*/
public class TrackInfo {
    private long lDistance;
    private long lTime;
    private String sUser_id;

    public long getLDistance(){
        return lDistance;
    }
    public void setLDistance(long lDistance){
        this.lDistance=lDistance;
    }

    public long getLTime(){
        return lTime;
    }
    public void setLTime(long lTime){
        this.lTime=lTime;
    }

    public String getSUser_id(){
        return sUser_id;
    }
    public void setSUser_id(String sUser_id){
        this.sUser_id=sUser_id;
    }

}
