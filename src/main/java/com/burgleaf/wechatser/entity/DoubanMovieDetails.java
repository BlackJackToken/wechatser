package com.burgleaf.wechatser.entity;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangbin
 * Date: 13-9-13
 * Time: 下午11:00
 * To change this template use File | Settings | File Templates.
 * 电影详情
 */
public class DoubanMovieDetails {

     private Map<String,String> rating;  //评分
     private int wish_count;    //想看人数
     private int collect_count; //看过人数
     private int ratings_count; //评分人数
     private String year;   //上演时间
     private Map<String,String> images; //图片
     private String alt; //链接地址
     private String mobile_url;//手机连接地址
     private String title; //名称
     private String summary;//简介
     private List<Map<String,String>> directors;  //导演
     private String directorsStr; //导演字符串

     private List<Map<String,String>> casts;  //主演
     private String castsStr;  //主演字符串

     private List<String> genres; //类型
     private String genresStr; //类型字符串

     private List<String> countries; //产地
     private String countriesStr;  //产地类型字符串

     private List<String> aka; //又名
     private String akaStr; //有名字符串

    public Map<String, String> getRating() {
        return rating;
    }

    public void setRating(Map<String, String> rating) {
        this.rating = rating;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Map<String, String>> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Map<String, String>> directors) {
        this.directors = directors;
    }

    public List<Map<String, String>> getCasts() {
        return casts;
    }

    public void setCasts(List<Map<String, String>> casts) {
        this.casts = casts;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getDirectorsStr() {

        StringBuffer sb = new StringBuffer();
        if(this.directors!=null){
            for(Map<String,String> map :this.directors ) {
                sb.append(map.get("name") + ",");
            }
        }

        return sb.length()>0?sb.toString().substring(0,sb.length()-1):sb.toString();
    }

    public void setDirectorsStr(String directorsStr) {
        this.directorsStr = directorsStr;
    }

    public String getCastsStr() {
        StringBuffer sb = new StringBuffer();
        if(this.directors!=null){
            for(Map<String,String> map :this.casts ) {
                sb.append(map.get("name")+",");
            }
        }
        return sb.length()>0?sb.toString().substring(0,sb.length()-1):sb.toString();
    }

    public void setCastsStr(String castsStr) {
        this.castsStr = castsStr;
    }

    public String getGenresStr() {

        StringBuffer sb = new StringBuffer();
        for(String genstr :genres){
            sb.append(genstr+",");
        }

        return sb.length()>0?sb.toString().substring(0,sb.length()-1):sb.toString();
    }

    public void setGenresStr(String genresStr) {
        this.genresStr = genresStr;
    }

    public String getCountriesStr() {
        StringBuffer sb = new StringBuffer();
        for(String counstr :this.getCountries()){
            sb.append(counstr+",");
        }

        return sb.length()>0?sb.toString().substring(0,sb.length()-1):sb.toString();
    }

    public String getAkaStr() {

        if(this.getAka()!=null){
           if(this.getAka().size()>0){
               return this.getAka().get(0);
           }
        }

        return null;
    }

    public void setAkaStr(String akaStr) {
        this.akaStr = akaStr;
    }

    public void setCountriesStr(String countriesStr) {
        this.countriesStr = countriesStr;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    @Override
    public String toString() {
        return "DoubanMovieDetails{" +
                "rating=" + rating +
                ", wish_count=" + wish_count +
                ", collect_count=" + collect_count +
                ", ratings_count=" + ratings_count +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", directors=" + directors +
                ", directorsStr='" + directorsStr + '\'' +
                ", casts=" + casts +
                ", castsStr='" + castsStr + '\'' +
                ", genres=" + genres +
                ", genresStr='" + genresStr + '\'' +
                ", countries=" + countries +
                ", countriesStr='" + countriesStr + '\'' +
                ", aka=" + aka +
                '}';
    }
}
