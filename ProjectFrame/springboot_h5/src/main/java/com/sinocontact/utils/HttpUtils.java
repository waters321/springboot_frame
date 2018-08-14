package com.sinocontact.utils;


import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Max on 2017/5/22.
 */
public class HttpUtils {
    private static final Logger logger = Logger.getLogger(HttpUtils.class);
    // get方式请求URL
    public static String sendGet(String url){
        logger.info("send get url:"+url);
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            //conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine())!= null) {
                result += line;
            }          
        }
        catch(Exception e) {
            logger.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try{
                if (in != null){in.close();}
                
            }
            catch (Exception ex){
                logger.error("",ex);
            }
        }
        logger.info("send get result :"+result);
        return result;
    }



    // 组织get方式请求参数
    public static String getParms(String returnStr, String key, String value){
        if (StringUtils.isEmpty(returnStr)==false){
            if (returnStr.contains("?")){
                returnStr += "&"+key+"="+value;
            }else{
                returnStr += "?"+key+"="+value;
            }
        }
        return returnStr;
    }

    // post方式请求url
    public static String sendPostJson(String url, String pram) {
        String result="";
        try {
            URL realUrl = new URL(url);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) realUrl
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(pram);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result= new String(data, "UTF-8"); // utf-8编码
            }
        } catch (Exception e) {
            logger.error("",e);
        }
        logger.info("send post json result:"+result);
        return result;
    }


}
