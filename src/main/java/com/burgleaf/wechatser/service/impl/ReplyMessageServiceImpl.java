package com.burgleaf.wechatser.service.impl;

import com.binf.wechat.constant.MessageType;
import com.binf.wechat.message.entity.req.ReqEventMessage;
import com.binf.wechat.message.entity.resp.RespMessage;
import com.binf.wechat.message.entity.resp.RespNewsMessage;
import com.binf.wechat.message.entity.resp.RespTextMessage;
import com.binf.wechat.message.entity.resp.model.Article;
import com.burgleaf.wechatser.common.Constant;
import com.burgleaf.wechatser.common.HttpRequester;
import com.burgleaf.wechatser.common.RespMessageModel;
import com.burgleaf.wechatser.entity.DoubanMovie;
import com.burgleaf.wechatser.entity.DoubanMovieDetails;
import com.burgleaf.wechatser.entity.DoubanQueryResult;
import com.burgleaf.wechatser.entity.HttpRespons;
import com.burgleaf.wechatser.service.ReplyMessageService;
import com.burgleaf.wechatser.utils.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangbin on 14-3-2.
 */
@Service
public class ReplyMessageServiceImpl implements ReplyMessageService {


    @Override
    public RespMessage ParseTextMessage(String content,String fromUserName,String toUserName) {


        Pattern pattern = Pattern.compile("^[:：].+");
        Matcher matcher = pattern.matcher(content);
        //如果以冒号开头直接返回原内容
        if (matcher.find()) {
            RespTextMessage respTextMessage = new RespTextMessage();
            respTextMessage.setFromUserName(toUserName);
            respTextMessage.setToUserName(fromUserName);
            respTextMessage.setCreateTime(new Date().getTime());
            respTextMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_TEXT.toString());
            respTextMessage.setContent("你说" + content);
            return respTextMessage;
        }
        else{
            return getDouBanMovie(content,fromUserName,toUserName);
        }

    }

    @Override
    public RespMessage ParseEventMessage(ReqEventMessage reqEventMessage) {

        String eventType = reqEventMessage.getEvent();
        // 取消订阅
        if (eventType.equals(MessageType.EVENT_TYPE_UNSUBSCRIBE)) {
           //可以什么都不做
        }
        else{
            return RespMessageModel.FOLLOW_RESP_TEXT_MSG
                                    .setField("FromUserName",reqEventMessage.getToUserName())
                                    .setField("ToUserName", reqEventMessage.getFromUserName());
        }

        return null;
    }

    /**
     * 将查询到的电影解析成对象
     * @param content
     * @param fromUserName
     * @param toUserName
     * @return
     */
    public RespMessage getDouBanMovie(String content,String fromUserName,String toUserName){

        //查询豆瓣电影信息
        List<DoubanMovieDetails> sovieDetails =  this.queryDoubanMovie(content);

        if(sovieDetails.size()>0){
            RespNewsMessage newsMessage = new RespNewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_NEWS.toString());
            newsMessage.setFuncFlag(0);
            List<Article> articleList = new ArrayList<Article>();
            // 单图文消息
            if(sovieDetails.size()==1){

                StringBuffer composite = new StringBuffer();

                DoubanMovieDetails doubanMovieDetails = sovieDetails.get(0);
                composite.append("上映时间："+doubanMovieDetails.getYear()+"年\n\n");
                composite.append("导演："+doubanMovieDetails.getDirectorsStr()+"\n\n");
                composite.append("主演："+doubanMovieDetails.getCastsStr()+"\n\n");
                String ratstr = doubanMovieDetails.getRating().get("average").equals("0")?"未上映\n\n":doubanMovieDetails.getRating().get("average")+"  评分人数:"+ doubanMovieDetails.getRatings_count()+"\n\n";
                composite.append("豆瓣平均分："+ratstr);
                composite.append("简介：~请点击这里查看豆瓣详情~");

                Article article = new Article();
                String akastr = doubanMovieDetails.getAkaStr()==null?"":"/"+doubanMovieDetails.getAkaStr();
                article.setTitle(doubanMovieDetails.getTitle()+akastr);
                article.setDescription(composite.toString());
                article.setPicUrl(doubanMovieDetails.getImages().get("large"));
                article.setUrl(doubanMovieDetails.getMobile_url());
                articleList.add(article);

                // 设置图文消息个数
                newsMessage.setArticleCount(articleList.size());
                // 设置图文消息包含的图文集合
                newsMessage.setArticles(articleList);
                // 将图文消息对象转换成xml字符串
                return  newsMessage;
            }
            else{
                Article article1 = new Article();
                article1.setTitle("查询\""+content+"\"相关的电影");
                article1.setDescription("");
                // 将图片置为空
                article1.setPicUrl("");
                article1.setUrl(Constant.DOUBAN_SUBJECT_SEARCH+"?search_text="+content);

                articleList.add(article1);
                for(DoubanMovieDetails moviedetail :sovieDetails){
                    Article article2 = new Article();
                    String akastr = moviedetail.getAkaStr()==null?"":"/"+moviedetail.getAkaStr();
                    article2.setTitle(moviedetail.getTitle()+akastr+"("+moviedetail.getYear()+"年)");
                    article2.setDescription("");
                    article2.setPicUrl(moviedetail.getImages().get("small"));
                    article2.setUrl(moviedetail.getMobile_url());
                    articleList.add(article2);
                }
                newsMessage.setArticleCount(articleList.size());
                newsMessage.setArticles(articleList);
                return  newsMessage;
            }
        }
        else{
            // 默认回复此文本消息
            RespTextMessage textMessage = new RespTextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_TEXT.toString());
            textMessage.setFuncFlag(0);
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
            textMessage.setContent("对不起，未找到与\""+content+"\"相关的电影");

            return   textMessage;
        }

    }


    /**
     * 查询豆瓣电影信息
     * @param content
     * @return
     */
    public List<DoubanMovieDetails> queryDoubanMovie(String content){

        List<DoubanMovieDetails> doubanMovieDetailses = new ArrayList<DoubanMovieDetails>();

        Map<String,String> map = new HashMap<String,String>();

        map.put("q",content);

        try{
            HttpRespons httpRespons =  HttpRequester.sendGet(Constant.DOUBAN_SEARCH, map);
            System.out.printf(httpRespons.getContentCollection().get(0));
            DoubanQueryResult rsult = JSONUtil.fromJSON(httpRespons.getContentCollection().get(0), DoubanQueryResult.class);

            if(rsult.getTotal()>0){

                int subsize = rsult.getSubjects().size()>9?9:rsult.getSubjects().size();

                for(int i=0;i<subsize;i++ ){
                    DoubanMovie movie = rsult.getSubjects().get(i);
                    String urlmovieid=Constant.DOUBAN_SUBJECT+movie.getId();
                    HttpRespons resultid =  HttpRequester.sendGet(urlmovieid,null);
                    DoubanMovieDetails movieDetails =  JSONUtil.fromJSON(resultid.getContentCollection().get(0), DoubanMovieDetails.class);
                    doubanMovieDetailses.add(movieDetails);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return doubanMovieDetailses;
    }


}
