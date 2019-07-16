package com.ioc.authorize.controller;

import com.ioc.authorize.enums.RedisEnum;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.common.IRedisService;
import com.ioc.authorize.service.user.IUserService;
import com.ioc.authorize.utils.CtokenUtil;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.RedisUtil;
import com.ioc.authorize.vo.ReturnVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ioc.authorize.constant.BaseConstant.*;


@RestController
@RequestMapping("/")
@Api(tags = "授权接口", description = "授权接口说明", hidden = true)
public class OauthController {

    private Logger LOG = Logger.getLogger( OauthController.class );

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IUserService userService;

    /**
     * 用户授权登录
     *
     * @param user
     * @return
     */
    @PostMapping("/do_oauth")
    @ApiOperation(value = "用户授权登录", notes = "用户授权登录接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
//    @ApiResponse( response = ReturnVo.class, code = 1, message = "接口返回对象参数")
    public ReturnVo doLogin(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response,@RequestBody User user) {
        ReturnVo returnVo = new ReturnVo( ERROR, "登录失败！" );

        try {

            String password = user.getPassword();
            String userNo = user.getUserNo();
            if (StringUtils.isBlank( userNo ) || StringUtils.isBlank( password )) {
                LogUtil.info( LOG, "登录名、密码不能为空，userNo:{0}", userNo );
                returnVo = new ReturnVo( ERROR, "登录名、密码不能为空！" );
            } else {
                User userByUserNo = userService.getUserByUserNo( user.getUserNo() );
                if (null != userByUserNo) {
                    if (password.equals( userByUserNo.getPassword() )) {
                        LogUtil.info( LOG, "登录成功，userNo:{0}", userNo );
                        returnVo = new ReturnVo( SUCCESS, "登录成功！", userByUserNo );

                        // 授权 token
                        String ctoken = CtokenUtil.sendCtoken( request, response );
                        redisService.setString( RedisUtil.formatRedisKey( RedisEnum.CTOKEN, ctoken ), userByUserNo, 600 );
                    } else {
                        LogUtil.info( LOG, "密码错误，userNo:{0}", userNo );
                        returnVo = new ReturnVo( ERROR, "密码错误！" );
                    }
                } else {
                    LogUtil.info( LOG, "用户不存在，userNo:{0}", userNo );
                    returnVo = new ReturnVo( ERROR, "用户不存在！" );
                }
            }
        } catch (Exception e) {
            LogUtil.error( LOG, e, "登录失败，userNo:{0}", user.getUserNo() );
        }
        return returnVo;
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PutMapping("/sign_out")
    @ApiOperation(value = "用户退出登录", notes = "用户登出接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
//    @ApiResponse( response=Person.class, code = 1, message = "接口返回对象参数")
    public ReturnVo signOut(@ApiIgnore HttpServletRequest request) {
        ReturnVo returnVo = new ReturnVo( ERROR, "退出登录失败！" );
        String ctoken = CtokenUtil.getCtoken( request );
        try {
            redisService.delete( RedisUtil.formatRedisKey( RedisEnum.CTOKEN, ctoken ) );
            returnVo.setCode( SUCCESS );
            returnVo.setMsg( "退出登录成功！" );
        } catch (Exception e) {
            LogUtil.info( LOG, "密码错误，ctoken:{0}", ctoken );
        }
        return returnVo;
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/do_register")
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
//    @ApiResponse( response=Person.class, code = 1, message = "接口返回对象参数")
    public ReturnVo doRegister(@RequestBody User user) {
        ReturnVo returnVo = new ReturnVo( ERROR, "注册失败！" );
        try {
            //查询注册的登录ID是否已经存在
            User userByUserNo = userService.getUserByUserNo( user.getUserNo() );
            if (null != userByUserNo) {
                returnVo = new ReturnVo( ERROR, "登录名已经存在" );
            }
            userService.addUser( user );
            returnVo = new ReturnVo( SUCCESS, "注册成功" );
        } catch (Exception e) {
            LogUtil.error( LOG, e, "注册失败，userNo:{0}", user.getUserNo() );
        }
        return returnVo;
    }
}
