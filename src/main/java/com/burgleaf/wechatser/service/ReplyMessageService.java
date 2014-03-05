package com.burgleaf.wechatser.service;

import com.binf.wechat.message.entity.req.ReqEventMessage;
import com.binf.wechat.message.entity.req.ReqTextMessage;
import com.binf.wechat.message.entity.resp.RespMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangbin on 14-3-2.
 */
public interface ReplyMessageService {

    /**
     * 解析文本内容
     * @return
     */
    public RespMessage ParseTextMessage(String content,String fromUserName,String toUserName);

    /**
     * 处理事件消息
     * @param reqEventMessage
     * @return
     */
    public RespMessage ParseEventMessage(ReqEventMessage reqEventMessage);




}
