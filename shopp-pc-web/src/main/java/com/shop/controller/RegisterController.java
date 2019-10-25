package com.shop.controller;

import com.shop.base.ResponseBase;
import com.shop.constant.Constants;
import com.shop.entity.UserEntity;
import com.shop.fegin.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 */
@Controller
public class RegisterController {
    @Autowired
    private MemberServiceFeign memberServiceFeign;
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";

    /**
     * 跳转注册页面
     *
     * @return register
     */
    @GetMapping(value = "/register")
    public String registerGet() {
        return REGISTER;
    }

    /**
     * 注册业务具体实现
     *
     * @return register
     */
    @PostMapping(value = "/register")
    public String registerPost(UserEntity userEntity, HttpServletRequest httpServletRequest) {
        /*
          1.验证参数
          2.调用会员接口
          3.如果失败，则跳转到失败页面
          4.如果成功，跳转到成功页面
         */
        ResponseBase responseBase = memberServiceFeign.registerUser(userEntity);
        if (!responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
            httpServletRequest.setAttribute("error","注册失败");
            return REGISTER;
        }
        return LOGIN;
    }
}
