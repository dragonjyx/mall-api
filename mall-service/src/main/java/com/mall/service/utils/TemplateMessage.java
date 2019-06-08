package com.mall.service.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by DRAGON on 2017/10/16.
 */
public class TemplateMessage {

    // 消息接收方
    private String toUser;
    // 模板id
    private String templateId;
    // 模板消息详情链接
    private String url;
    //小程序配置
    private JSONObject miniprogram;
    // 参数列表
    private List<TemplateParams> templateParamList;

    public void setMiniprogram(JSONObject miniprogram) {
        this.miniprogram = miniprogram;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public List<TemplateParams> getTemplateParamList() {
        return templateParamList;
    }

    public void setTemplateParamList(List<TemplateParams> templateParamList) {
        this.templateParamList = templateParamList;
    }

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");
        buffer.append(String.format("\"url\":\"%s\"", this.url)).append(",");
        if(this.miniprogram != null){
            buffer.append(String.format("\"miniprogram\": %s", this.miniprogram.toJSONString())).append(",");
        }
        buffer.append("\"data\":{");
        TemplateParams param = null;
        for (int i = 0; i < this.templateParamList.size(); i++) {
            param = templateParamList.get(i);
            // 判断是否追加逗号
            if (i < this.templateParamList.size() - 1){
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},", param.getName(), param.getValue(), param.getColor()));
            }else{
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));
            }

        }
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

}
