package com.shop.api.service;

import com.shop.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author admin
 */
@RequestMapping("/member")
public interface TestApiService {
    @RequestMapping("/test")
    public Map<String, Object> test(Integer id, String name);

    @RequestMapping("/testResponseBase")
    public ResponseBase testResponseBase();

    @RequestMapping("/testRedis")
    public ResponseBase testRedis(String key,String value);
}
