package com.shop.controller;

import com.shop.base.ResponseBase;
import com.shop.constant.Constants;
import com.shop.entity.UserEntity;
import com.shop.fegin.MemberServiceFeign;
import com.shop.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * @author admin
 */
@Controller
public class LoginController {
    private static final String LOGIN = "login";
    private static final String INDEX = "redirect:/";
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    /**
     * 跳转到登录页面
     *
     * @return String
     */
    @GetMapping(value = "/login")
    public String loginGet() {
        return LOGIN;
    }

    /**
     * 登录的具体实现
     *
     * @return String
     */
    @PostMapping(value = "/login")
    public String loginPost(UserEntity userEntity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        /**
         * 1.验证参数
         * 2.调用登录接口，获取token信息
         * 3.如果账号密码正确，将token放入到cookie
         */
        ResponseBase login = memberServiceFeign.login(userEntity);
        if (!Constants.HTTP_RES_CODE_200.equals(login.getRtnCode())) {
            httpServletRequest.setAttribute("error", "账号或者密码错误！");
            return LOGIN;
        }
        LinkedHashMap loginData = (LinkedHashMap) login.getData();
        String memberToken = (String) loginData.get("memberToken");
        if (StringUtils.isEmpty(memberToken)) {
            httpServletRequest.setAttribute("error", "会话已经失效！");
            return LOGIN;
        }
        CookieUtil.addCookie(httpServletResponse, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
        return INDEX;
    }

}
