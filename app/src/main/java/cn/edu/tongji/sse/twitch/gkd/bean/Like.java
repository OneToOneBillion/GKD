package cn.edu.tongji.sse.twitch.gkd.bean;

public class Like {
    private String sUsername;
    private String[] sLikesname;

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String[] getsLikesname() {
        return sLikesname;
    }

    public void setsLikesname(String[] sLikesname) {
        this.sLikesname = sLikesname;
    }
}
