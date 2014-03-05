package com.burgleaf.wechatser.common;

import com.binf.wechat.constant.MessageType;
import com.binf.wechat.message.entity.resp.RespTextMessage;

import java.util.Date;

/**
 * Created by wangbin on 14-3-2.
 */
public class RespMessageModel {

    /**
     * 信息识别错误信息
     */
    public static RespTextMessage ERROR_RESP_TEXT_MSG  =
           new RespTextMessage().setField("MsgType",MessageType.RESP_MESSAGE_TYPE_TEXT.toString())
                                .setField("Content","对不起系统不能识别您所发送的内容!")
                                .setField("CreateTime",new Date().getTime());                                            ;


    /**
     * 信息识别错误信息
     */
    public static RespTextMessage FOLLOW_RESP_TEXT_MSG  =
            new RespTextMessage().setField("MsgType",MessageType.RESP_MESSAGE_TYPE_TEXT.toString())
                    .setField("Content","非常感谢您能关注该微信!")
                    .setField("CreateTime",new Date().getTime());
}
