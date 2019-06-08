package com.mall.webapi.controller.pay.wechat;

import com.java.utils.code.MD5Util;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class WechatUtils {

    /**
     * 微信MD5签名
     *
     * @param parameters
     * @param apiKey
     * @return
     */
    public static String createSign(SortedMap<Object, Object> parameters, String apiKey) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        String sign = MD5Util.md5Encrypt32(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 产生随机数
     *
     * @return
     */
    public static String createNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * xml组合
     *
     * @param parameters
     * @return
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 获取对账单
     *
     * @param billUrl
     * @param xml
     * @return
     */
    public static String getBill(String billUrl, String xml) {
        String result = null;
        BufferedReader br = null;
        HttpURLConnection con = null;
        OutputStreamWriter out = null;

        try {
            URL url = new URL(billUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            out = new OutputStreamWriter(con.getOutputStream(), Charset.defaultCharset());
            out.write(new String(xml.getBytes(Charset.defaultCharset())));
            out.flush();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.defaultCharset()));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line); // 将读到的内容添加到 buffer 中
                buffer.append("\r\n"); // 添加分割
                System.out.println(line);
            }
            result = buffer.toString();
        } catch (Exception e) {
            log.error("微信账单请求失败:{}", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                    con.disconnect();
                }
            } catch (Exception e) {
                log.error("请求关闭失败:{}", e);
            }
        }
        return result;
    }


    /**
     * 写文本
     *
     * @param url
     * @param content
     */
    public static void writeBillTxt(String url, String content) {
        File file = new File(url);//url 如果不存在需要创建一个
        try {
            file.createNewFile();//创建一个新的文件

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);//\r\n换行

            bw.flush();// 把缓存区内容压入文件
            bw.close();//关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
