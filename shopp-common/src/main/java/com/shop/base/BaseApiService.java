package com.shop.base;

import com.shop.constant.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gaoshuai
 */
@Component
public class BaseApiService {
    @Autowired
    protected BaseRedisService baseRedisService;
    /**
     *
     * @return sueccess
     */
    public ResponseBase setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    /**
     * 返回信息
     * @param msg
     * @return
     */
    public ResponseBase setResultSuccess(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }
    /**
     *
     * @param data
     * @return
     */
    public ResponseBase setResultSuccess(Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

       /**
     *
     * @param msg
     * @return false
     */
    public ResponseBase setResultError(String msg){
        return setResult(Constants.HTTP_RES_CODE_500,msg, null);
    }

    /**
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public ResponseBase setResult(Integer code, String msg, Object data) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setRtnCode(code);
        responseBase.setMsg(msg);
        if (data != null) {
            responseBase.setData(data);
        }
        return responseBase;
    }

    /**
     *
     * @param code
     * @param msg
     * @return
     */
    public ResponseBase setResultError(Integer code,String msg){
        return setResult(code,msg, null);
    }

}
