package com.burgleaf.wechatser.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangbin on 14-3-1.
 */
public class HttpUtils {


    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 打印输入流的内容
     * @param inputStream
     * @return
     */
    public static InputStream printInput(InputStream inputStream){


        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();

        StringBuilder sb = new StringBuilder();
        BufferedInputStream bf =  new  BufferedInputStream(inputStream);
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            bf.mark(102400);
            while ((len = bf.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
                sb.append(new String(buffer,"utf-8"));
            }

            bf.reset();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.printf("--------------------------------------------------------------------");
        logger.info("[微信服务器端的请求为:]{}",sb.toString());
        System.out.printf("--------------------------------------------------------------------");
        return bf;

    }
}
