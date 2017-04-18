package com.project.coresol.apiexample.Models;

/**
 * Created by coresol on 18/04/17.
 */

public class YoutubeModel {
    String title,desc,thumbUrl;

    public YoutubeModel(String title, String desc, String thumbUrl) {
        this.title = title;
        this.desc = desc;
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
