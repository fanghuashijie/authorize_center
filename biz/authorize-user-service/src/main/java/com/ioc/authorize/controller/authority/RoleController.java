package com.ioc.authorize.controller.authority;

import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.authority.Role;
import com.ioc.authorize.service.authority.IRoleService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.vo.common.ReturnVo;
import com.ioc.authorize.vo.menu.MenuTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ioc.authorize.constant.BaseConstant.NO_DEL_FLAG;
import static com.ioc.authorize.vo.common.ReturnVo.CODE_EXCEPTION;
import static com.ioc.authorize.vo.common.ReturnVo.CODE_SUCCESS;

/**
* @Description: 角色管理
* @Author: DeYi Peng
* @CreateDate: 2019/7/16 17:00
* @Version: 1.0
*/
@RestController
@RequestMapping("/role")
@Api(tags = "角色接口", description = "角色接口说明", hidden = true)
public class RoleController {

    private Logger LOG = Logger.getLogger( getClass() );

    @Autowired
    private IRoleService roleService;

    /**
     * 角色查询列表
     */
    @GetMapping("/ajaxList")
    @ApiOperation(value = "角色查询列表", notes = "角色查询列表接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo ajaxList(Role role){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "查询失败！");
        try {
            List<Role> roleList = roleService.getAll( role );

            returnVo = new ReturnVo(CODE_SUCCESS, "查询成功！",roleList);
        } catch (AuthorizeUserException e) {
            LogUtil.error(LOG, e, "角色查询失败");
            returnVo.setMsg(e.getErrMsg());
        }  catch (Exception e) {
            LogUtil.error(LOG, e, "角色查询失败");
        }
        return  returnVo;
    }

    /**
     * 角色添加
     */
    @PostMapping("/addRole")
    @ApiOperation(value = "角色添加", notes = "角色添加接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo addMenu(Role role){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "添加失败！");
        try {

            // 检查必填项是否为空


            // 根据sessionId获取用户信息
//            String ctoken = CtokenUtil.getCtoken( request );
//            User user = redisService.get( RedisUtil.formatRedisKey( RedisEnum.SESSION, ctoken ));
            roleService.addRole( role );

            returnVo = new ReturnVo( CODE_SUCCESS, "添加成功！");
        } catch (AuthorizeUserException e) {
            LogUtil.error(LOG, e, "角色添加失败");
            returnVo.setMsg(e.getErrMsg());
        }  catch (Exception e) {
            LogUtil.error(LOG, e, "角色添加失败");
        }
        return  returnVo;
    }

    @PutMapping("/removeRole/{ids}")
    @ApiOperation(value = "角色删除", notes = "角色删除接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo removeMenu( @PathVariable String ids ){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "删除失败！");
        try {
            roleService.removeRole( ids );

            returnVo = new ReturnVo( CODE_SUCCESS, "角色删除成功！");
        } catch (AuthorizeUserException e) {
            returnVo.setMsg(e.getErrMsg());
        } catch (Exception e) {
            LogUtil.error(LOG, e, "角色删除失败");
        }
        return  returnVo;
    }

}
