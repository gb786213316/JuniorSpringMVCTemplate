package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.AuthToken;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;
import com.haomostudio.JuniorSpringMVCTemplate.service.AuthTokenService;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @author: gongbin
 * @date: 2018/8/2 19:27
 * @copyright: 长安新生（深圳）金融投资有限公司
 */
@Api(description = "account-Controller")
@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private HmUserService hmUserService;
    @Autowired
    private AuthTokenService authTokenService;
    @Autowired
    HttpServletResponse response;
    
    /**
     * @Description :登录
     * @author: gongbin
     * @date: 2018/8/2 19:55
     */
    @RequestMapping(value = "LoginUsingPOST",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "loginId", required = false) String loginId,
            @RequestParam(value = "password", required = false) String password
            ){
        logger.info("AccountController param:loginId="+loginId+",password="+password);
        HmUser user = new HmUser();
        AuthToken authToken = new AuthToken();
        if (null==loginId){
            response.setStatus(404);
            return Resp.fail("ERR:the loginId is null");
        }
        user.setLoginid(loginId);
        if (null==password){
            response.setStatus(404);
            return Resp.fail("ERR:the password is null");
        }
        user.setPassword(password);
        if (null==token){
            response.setStatus(404);
            return Resp.fail("ERR:the token is null");

        }
        user = hmUserService.login(user);
        if (null== user){
            response.setStatus(404);
            return Resp.fail("ERR:login fail");
        }
        String usrId = user.getId();
        authToken.setId(usrId);
        authToken = authTokenService.getOneByToken(authToken);
        return authToken;
    }



}
