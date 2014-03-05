package com.burgleaf.wechatser.common;


import com.burgleaf.wechatser.entity.HttpRespons;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: wangbin
 * Date: 13-9-8
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequester {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpRequester.class);

    private static HttpClient hc = new DefaultHttpClient();

    public static  HttpRespons sendGet(String urlString, Map<String, String> params)throws Exception {
       return send(urlString, "GET", params, null);
    }

    public static  HttpRespons sendPost(String urlString, Map<String, String> params)throws Exception {
        return send(urlString, "POST", params, null);
    }

    public static HttpRespons  send(String urlString, String method,
                       Map<String, String> parameters, Map<String, String> propertys)throws Exception{


        List<NameValuePair> params = new ArrayList<NameValuePair>();


        if(parameters!=null){

            for (String key : parameters.keySet()) {
                params.add(new BasicNameValuePair(key,parameters.get(key)));
            }
        }

        HttpResponse httpresponse =null;


        if("GET".equals(method)){
            HttpGet httpget = new HttpGet(urlString);


            String str = EntityUtils.toString(new UrlEncodedFormEntity(params,"UTF-8"));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));

            httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpget.setHeader("Connection", "keep-alive");
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
            httpget.setHeader("Accept-Charset", "utf-8");
            httpget.setHeader("ContentType", "utf-8");

            // 发送请求
             httpresponse = hc.execute(httpget);
            System.out.printf("--------------------------------------------------------------------");
            logger.info("发送get请求{}",httpget.getURI().toString());
            System.out.printf("--------------------------------------------------------------------");

        }else{
            // Post请求
            HttpPost httppost = new HttpPost(urlString);

            httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httppost.setHeader("Connection", "keep-alive");
            httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
            httppost.setHeader("Accept-Charset", "utf-8");
            httppost.setHeader("ContentType", "utf-8");
            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params));
            // 发送请求
            httpresponse = hc.execute(httppost);
            System.out.printf("--------------------------------------------------------------------");
            logger.info("发送post请求{}",httppost.getURI().toString());
            System.out.printf("--------------------------------------------------------------------");
        }
        return makeContent(urlString, httpresponse);
    }

    /**
     * 发送http请求获得response
     * @param urlString
     * @return
     */
    private static HttpRespons makeContent(String urlString,HttpResponse response)throws IOException{

        HttpRespons httpResponser = new HttpRespons();

        HttpEntity entity =  response.getEntity();

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));

            httpResponser.setContentCollection(new Vector<String>());
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line!=null){
                httpResponser.getContentCollection().add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            httpResponser.setUrlString(urlString);
            httpResponser.setCode(response.getStatusLine().getStatusCode());
            httpResponser.setContentType(entity.getContentType().getValue());
            httpResponser.setHeaders(response.getAllHeaders());
            System.out.printf("--------------------------------------------------------------------");
            logger.info("请求结果{}",httpResponser.toString());
            System.out.printf("--------------------------------------------------------------------");
            return httpResponser;
        }
        catch (IOException e){
           throw new RuntimeException(e);
        }

    }





}
