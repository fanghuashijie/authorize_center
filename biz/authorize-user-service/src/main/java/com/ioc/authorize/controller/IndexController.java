package com.ioc.authorize.controller;

import com.ioc.authorize.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(tags = "首页接口", description = "首页接口说明", hidden = true)
public class IndexController {

    private Logger LOG = Logger.getLogger( IndexController.class );

    @GetMapping("/index")
    @ApiOperation(value = "首页", notes = "首页接口")
    public String index() {
        LogUtil.info( LOG, "首页跳转" );
        return "/index";
    }
}
