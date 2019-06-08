package com.mall.webapi.controller.pay.wechat;

import java.io.*;

public class IWxPayConfig extends WXPayConfig{
    private String app_id;

    private String wx_pay_key;

    private String wx_pay_mch_id;

    private String certPath;

    private byte[] certData;

    public IWxPayConfig(String app_id, String wx_pay_key, String wx_pay_mch_id, String certPath) {
        this.app_id = app_id;
        this.wx_pay_key = wx_pay_key;
        this.wx_pay_mch_id = wx_pay_mch_id;
        this.certPath = certPath;

        if(this.certPath != null){
            File file = new File(certPath);
            InputStream certStream = null;
            try {
                certStream = new FileInputStream(file);
                this.certData = new byte[(int) file.length()];
                try {
                    certStream.read(this.certData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    certStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    String getAppID() {
        return this.app_id ;
    }

    @Override
    String getMchID() {
        return this.wx_pay_mch_id;
    }

    @Override
    String getKey() {
        return  this.wx_pay_key;
    }

    @Override
    InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
