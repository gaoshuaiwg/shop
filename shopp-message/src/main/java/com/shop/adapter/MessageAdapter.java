package com.shop.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一发送消息适配器
 * @author admin
 */
public interface MessageAdapter {
    /**
     * 发送消息
     * @param body
     */
    public void sendMes(JSONObject body);
}
