package com.burgleaf.wechatser.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wangbin
 * Date: 13-9-10
 * Time: 上午12:00
 * To change this template use File | Settings | File Templates.
 */
public class DoubanQueryResult {

    private int total; //查询电影数量

    private String title;//查询结果

    private List<DoubanMovie> subjects; //电影信息


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DoubanMovie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<DoubanMovie> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "DoubanQueryResult{" +
                "total=" + total +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
