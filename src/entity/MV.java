package entity;

import java.util.Date;

public class MV {
    private int id;
    private String title;
    private String producer;
    private Date time;
    private String url;
    private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(String title) {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducer(String producer) {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getTime(String time) {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUrl(String url) {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserid(String userid) {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "MV{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", producer='" + producer + '\'' +
                ", time=" + time +
                ", url='" + url + '\'' +
                ", userid=" + userid +
                '}';
    }
}
