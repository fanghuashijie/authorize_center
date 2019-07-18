package com.ioc.authorize.controller.authority;

import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.service.authority.IMenuService;
import com.ioc.authorize.service.common.IRedisService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.vo.common.ReturnVo;
import com.ioc.authorize.vo.menu.MenuTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

import static com.ioc.authorize.constant.BaseConstant.*;
import static com.ioc.authorize.vo.common.ReturnVo.CODE_EXCEPTION;
import static com.ioc.authorize.vo.common.ReturnVo.CODE_SUCCESS;


@RestController
@RequestMapping("/authority/menu")
@Api(tags = "菜单接口", description = "菜单接口说明", hidden = true)
public class MenuController {

    private Logger LOG =  Logger.getLogger(getClass());

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IMenuService menuService;

    /**
     * 菜单查询列表
     */
    @GetMapping("/ajaxList")
    @ApiOperation(value = "菜单查询列表", notes = "菜单查询列表接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo ajaxList(){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "查询失败！");
        try {

            Menu menu = new Menu();
            menu.setDelFlag( NO_DEL_FLAG );
            List<MenuTreeVo>  menuList = menuService.getAll( menu );
            // 根据sessionId获取用户信息
//            String ctoken = CtokenUtil.getCtoken( request );
//            redisService.set( RedisUtil.formatRedisKey( RedisConstant.SESSION, ctoken ), userByUserNo );
//            User user = redisService.get( RedisUtil.formatRedisKey( RedisConstant.SESSION, ctoken ), User.class );

            returnVo = new ReturnVo(CODE_SUCCESS, "查询成功！",menuList);
        } catch (AuthorizeUserException e) {
            LogUtil.error(LOG, e, "菜单查询失败");
            returnVo.setMsg(e.getErrMsg());
        }  catch (Exception e) {
            LogUtil.error(LOG, e, "菜单查询失败");
        }
        return  returnVo;
    }

    /**
     * 菜单添加
     */
    @PostMapping("/addMenu")
    @ApiOperation(value = "菜单添加", notes = "菜单添加接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo addMenu(@ApiIgnore HttpServletRequest request, @RequestBody Menu menu){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "添加失败！");
        try {

            // 检查必填项是否为空
            String menuName = menu.getName();
            String menuSn = menu.getSn();
            Integer menuSeq = menu.getSeq();
            Integer menuIsleaf = menu.getIsLeaf();
            Integer status = menu.getStatus();
            Boolean someStringNull = isSomeStringNull( menuName, menuSn, menuSeq.toString(), menuIsleaf.toString(), status.toString() );
            if (someStringNull){
                LogUtil.error(LOG, "必填项不能为空");
                throw new AuthorizeUserException( "菜单必填字段不能为空" );
            }

            menu.setDelFlag( NO_DEL_FLAG );

            // 根据sessionId获取用户信息
//            String ctoken = CtokenUtil.getCtoken( request );
//            User user = redisService.get( RedisUtil.formatRedisKey( RedisEnum.SESSION, ctoken ));
            menuService.addMenu( menu, null );

            returnVo = new ReturnVo( CODE_SUCCESS, "添加成功！");
        } catch (AuthorizeUserException e) {
            LogUtil.error(LOG, e, "菜单添加失败");
            returnVo.setMsg(e.getErrMsg());
        }  catch (Exception e) {
            LogUtil.error(LOG, e, "菜单添加失败");
        }
        return  returnVo;
    }

    @PutMapping("/removeMenu/{ids}")
    @ApiOperation(value = "菜单添加", notes = "菜单添加接口")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="month",value="年月，格式为：201801",dataType="String", paramType = "query"),
    })
    @ApiResponse( response = ReturnVo.class, code = CODE_SUCCESS, message = "接口返回对象参数")
    public ReturnVo removeMenu( @PathVariable String ids ){
        ReturnVo returnVo = new ReturnVo(CODE_EXCEPTION, "删除失败！");
        try {
            menuService.removeMenu( ids );

            returnVo = new ReturnVo( CODE_SUCCESS, "菜单删除成功！");
        } catch (AuthorizeUserException e) {
            returnVo.setMsg(e.getErrMsg());
        } catch (Exception e) {
            LogUtil.error(LOG, e, "菜单删除失败");
        }
        return  returnVo;
    }


    /**
     * 判断可变参数中是否其中某个为空
     * @param str
     * @return
     */
    private Boolean isSomeStringNull(String ...str){
        Boolean isNull = Boolean.FALSE;
        for (String temp : str){
            if (StringUtils.isBlank( temp ) && temp.length() == 0){
                isNull = Boolean.TRUE;
                break;
            }
        }
        return isNull;
    }

}
