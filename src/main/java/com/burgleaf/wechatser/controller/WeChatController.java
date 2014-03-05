package com.burgleaf.wechatser.controller;

import com.binf.wechat.constant.MessageType;
import com.binf.wechat.message.entity.Message;
import com.binf.wechat.message.entity.req.ReqEventMessage;
import com.binf.wechat.message.entity.req.ReqTextMessage;
import com.binf.wechat.message.entity.resp.RespMessage;
import com.binf.wechat.message.entity.resp.RespTextMessage;
import com.binf.wechat.message.handle.HandleMessage;
import com.binf.wechat.utils.XmlUtils;
import com.binf.wechat.verify.VerifyToken;
import com.burgleaf.wechatser.common.Constant;
import com.burgleaf.wechatser.common.RespMessageModel;
import com.burgleaf.wechatser.service.ReplyMessageService;
import com.burgleaf.wechatser.service.impl.CustomHandleMessageImpl;
import com.burgleaf.wechatser.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by wangbin on 14-3-1.
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);


    @Autowired
    ReplyMessageService replyTextMessageService;

    @RequestMapping(value = "/receive.do", method = RequestMethod.GET)
    public void receiveGet(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) throws Exception {

        // 将token、timestamp、nonce三个参数进行字典序排序
        String[] strArray = new String[]{Constant.TOKEN, timestamp, nonce};

        Arrays.sort(strArray);

        if (VerifyToken.checkServeToken(strArray, signature)) {
            response.getWriter().print(echostr);
            response.getWriter().close();
        }

    }


    @RequestMapping(value = "/receive.do", method = RequestMethod.POST)
    public void receivePost(HttpServletRequest request,
                            HttpServletResponse response) {

        PrintWriter out = null;
        Message message = null;
        try {
            out = response.getWriter();

            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            //打印输入流
            InputStream inputStr = HttpUtils.printInput(request.getInputStream());

            //默认的信息处理类
            HandleMessage handleMessage = new CustomHandleMessageImpl();

            //将微信发送的信息转换成实体类
            message = handleMessage.reqMessageHandle(inputStr);

            //利用微信内容返回对应的内容
            String responseXml =  getResponseXml(message);

            if(null==responseXml){
                throw new RuntimeException();
            }

            System.out.printf("--------------------------------------------------------------------");
            logger.info("[发送给客户端:]{}",responseXml);
            System.out.printf("--------------------------------------------------------------------");
            out.print(responseXml);


            }catch (Exception e){
                RespTextMessage respTextMessage = RespMessageModel.ERROR_RESP_TEXT_MSG;
                respTextMessage.setFromUserName(message.getToUserName());
                respTextMessage.setFromUserName(message.getFromUserName());
                String responseXml = XmlUtils.messageToXml(respTextMessage);
                System.out.printf("--------------------------------------------------------------------");
                logger.info("[发送给客户端:]{}", responseXml);
                System.out.printf("--------------------------------------------------------------------");
                out.print(responseXml);

            }
            finally {
                out.close();
            }

    }


    public String getResponseXml(Message message){
        String msgType = message.getMsgType();
        //处理文本信息
        if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_TEXT.toString())) {
            ReqTextMessage reqTextMessage = (ReqTextMessage) message;
            String content= reqTextMessage.getContent();
            String fromUserName = reqTextMessage.getFromUserName();
            String toUserName = reqTextMessage.getToUserName();

            RespMessage respMessage =replyTextMessageService.ParseTextMessage(content,fromUserName,toUserName);

            String responseXml = XmlUtils.messageToXml(respMessage);

            // 响应消息
            return responseXml;
         //处理事件信息
        }else if(msgType.equals(MessageType.REQ_MESSAGE_TYPE_EVENT.toString())){
            ReqEventMessage reqEventMessage = (ReqEventMessage)message;

            RespMessage respMessage =replyTextMessageService.ParseEventMessage(reqEventMessage);

            String responseXml = XmlUtils.messageToXml(respMessage);
            return responseXml;
        }



        return null;


    }


}
