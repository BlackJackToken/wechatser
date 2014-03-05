package com.burgleaf.wechatser.service.impl;


import com.binf.wechat.message.entity.req.*;
import com.binf.wechat.message.handle.impl.DefHandleMessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wangbin on 14-3-2.
 */
//自定处理微信请求信息,例如插入数据库操作
public class CustomHandleMessageImpl extends DefHandleMessageImpl {


    private static final Logger logger = LoggerFactory.getLogger(CustomHandleMessageImpl.class);


    @Override
    public ReqTextMessage textMessageHandle(Map<String, Object> messageMap) {

        ReqTextMessage reqTextMessage = super.textMessageHandle(messageMap);

        logger.debug("messageMap解析成功:{}",reqTextMessage.toString());

        return reqTextMessage;
    }

    @Override
    public ReqImageMessage imageMessageHandle(Map<String, Object> messageMap) {
        return super.imageMessageHandle(messageMap);
    }

    @Override
    public ReqVoiceMessage voiceMessageHandle(Map<String, Object> messageMap) {
        return super.voiceMessageHandle(messageMap);
    }

    @Override
    public ReqVideoMessage videoMessageHandle(Map<String, Object> messageMap) {
        return super.videoMessageHandle(messageMap);
    }

    @Override
    public ReqLocationMessage locationMessageHandle(Map<String, Object> messageMap) {
        return super.locationMessageHandle(messageMap);
    }

    @Override
    public ReqLinkMessage linkMessageHandle(Map<String, Object> messageMap) {
        return super.linkMessageHandle(messageMap);
    }
}
