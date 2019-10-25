package com.shop.api.service.impl;

import com.shop.base.BaseApiService;
import com.shop.base.ResponseBase;
import com.shop.api.service.TestApiService;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService {
    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("rtnCode", "200");
        result.put("msg", "success");
        result.put("data", new String[]{"1234", "2342", id + "",name});
        return result;
    }

    @Override
    public ResponseBase testResponseBase() {

        return setResultSuccess();
    }

    @Override
    public ResponseBase testRedis(String key, String value) {
         baseRedisService.setString(key,value,null);
        return setResultSuccess();
    }


}
