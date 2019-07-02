package com.ioc.authorize.controller;

import com.ioc.authorize.enums.RedisEnum;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.common.IRedisService;
import com.ioc.authorize.service.user.IUserService;
import com.ioc.authorize.utils.CtokenUtil;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.RedisUtil;
import com.ioc.authorize.vo.ReturnVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ioc.authorize.constant.BaseConstant.*;


@RestController
@RequestMapping("/authorize/login")
public class LoginController {

    private Logger LOG =  Logger.getLogger( LoginController.class);

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/doLogin")
    public Object doLogin(HttpServletRequest request, HttpServletResponse response, User user){
        ReturnVo returnVo = new ReturnVo(ERROR, "登录失败！");
        try {
//            user.setDelFlag(NO_DEL_FLAG);
            String password = user.getPassword();
            String userNo = user.getUserNo();
            if (StringUtils.isBlank(userNo) || StringUtils.isBlank(password)){
                LogUtil.info(LOG,"登录名、密码不能为空，userNo:{0}", userNo);
                returnVo = new ReturnVo(ERROR, "登录名、密码不能为空！");
            }else {
                User userByUserNo = userService.getUserByUserNo(user.getUserNo());
                if (null != userByUserNo){
                    if (password.equals(userByUserNo.getPassword())){
                        LogUtil.info(LOG,"登录成功，userNo:{0}", userNo);
                        returnVo = new ReturnVo(SUCCESS, "登录成功！", userByUserNo);
                        String ctoken = CtokenUtil.sendCtoken( request, response );
                        redisService.set( RedisUtil.formatRedisKey( RedisEnum.SESSION, ctoken ), userByUserNo );
                    }else {
                        LogUtil.info(LOG,"密码错误，userNo:{0}", userNo);
                        returnVo = new ReturnVo(ERROR, "密码错误！");
                    }
                }else {
                    LogUtil.info(LOG,"用户不存在，userNo:{0}", userNo);
                    returnVo = new ReturnVo(ERROR, "用户不存在！");
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "登录失败，userNo:{0}", user.getUserNo());
        }
//        return FastJsonUtil.convertObjectToJSON(returnVo);
        return returnVo;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping("/doRegister")
    public Object doRegister(User user){
        ReturnVo returnVo = new ReturnVo(ERROR, "注册失败！");
        try {
            //查询注册的登录ID是否已经存在
            User userByUserNo = userService.getUserByUserNo( user.getUserNo() );
            if (null != userByUserNo){
                returnVo = new ReturnVo(ERROR, "登录名已经存在");
            }
            userService.addUser(user);
            returnVo = new ReturnVo(ERROR, "注册成功");
        } catch (Exception e) {
            LogUtil.error(LOG, e, "注册失败，userNo:{0}", user.getUserNo());
        }
        return returnVo;
    }
}
