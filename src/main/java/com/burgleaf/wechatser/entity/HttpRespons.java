package com.burgleaf.wechatser.entity;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: wangbin
 * Date: 13-9-9
 * Time: 下午10:37
 * To change this template use File | Settings | File Templates.
 */
public class HttpRespons {

    String urlString; //url地址


    String locale;

    Header[] headers;


    String contentType;

    int code;



    Vector<String> contentCollection;     //内容信息

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public Vector<String> getContentCollection() {
        return contentCollection;
    }

    public void setContentCollection(Vector<String> contentCollection) {
        this.contentCollection = contentCollection;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "HttpRespons{" +
                "urlString='" + urlString + '\'' +
                ", locale='" + locale + '\'' +
                ", headers=" + Arrays.toString(headers) +
                ", contentType='" + contentType + '\'' +
                ", code=" + code +
                ", contentCollection=" + contentCollection +
                '}';
    }
}