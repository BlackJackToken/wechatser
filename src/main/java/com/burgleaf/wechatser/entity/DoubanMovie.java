package com.burgleaf.wechatser.entity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangbin
 * Date: 13-9-10
 * Time: 上午12:04
 * To change this template use File | Settings | File Templates.
 */
public class DoubanMovie {

    private String alt;  //详情页面

    private String collect_count ; //看过人数

    private String id ; //电影id

    private Map<String,String> images; //图片

    private String title;//电影名称

    private String year;//上映年代


    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "DoubanMovie{" +
                "alt='" + alt + '\'' +
                ", collect_count='" + collect_count + '\'' +
                ", id='" + id + '\'' +
                ", images=" + images +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
