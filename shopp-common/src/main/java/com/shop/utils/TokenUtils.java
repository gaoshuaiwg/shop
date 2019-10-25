package com.shop.utils;

import com.shop.constant.Constants;

import java.util.UUID;

/**
 * @author gaoshuai
 */
public class TokenUtils {
    /**
     * 生成会员token
     *
     * @return
     */
    public static String getMemberToken() {
        return Constants.TOKEN_MEMBER + "-" + UUID.randomUUID();
    }
}
