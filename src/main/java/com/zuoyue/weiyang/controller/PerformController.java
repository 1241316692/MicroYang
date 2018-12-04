package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.bean.Perform;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/perform")
@Api(tags = "演出接口")
public class PerformController extends BaseApiController {

    @Autowired
    PerformService performService;

    @PostMapping("/register")
    @ApiOperation(value = "添加演出", notes = "时间格式yyyy-MM-dd")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "主题", required = true) String theme,
                                        @RequestParam @ApiParam(value = "内容", required = true) String content,
                                        @RequestParam @ApiParam(value = "主办方",required = true) String sponsor,
                                        @RequestParam @ApiParam(value = "是否接受报名",required = true) Boolean accept,
                                        @RequestParam @ApiParam(value = "时间",required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date time,
                                        @RequestParam @ApiParam(value = "地点",required = true) String place)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (StringUtils.isBlank(theme)) return onBadResp("主题不能为空");
        if (StringUtils.isBlank(content)) return onBadResp("内容不能为空");
        if (StringUtils.isBlank(sponsor)) return onBadResp("主办方不能为空");
        if (accept == null) return onBadResp("是否接受报名不能为空");
        if (time == null) return onBadResp("时间不能为空");
        if (StringUtils.isBlank(place)) return onBadResp("地点不能为空");

        Perform perform = new Perform()
                .setUserId(userId)
                .setTheme(theme.trim())
                .setContent(content.trim())
                .setSponsor(sponsor.trim())
                .setAccept(accept)
                .setTime(time)
                .setPlace(place);
        performService.insert(perform);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询演出列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(performService.list(null)));
    }

    @GetMapping("/listAccept")
    @ApiOperation(value = "查询演出接受报名的列表", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> listAccept(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(performService.listAccept(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改演出信息", notes = "时间格式yyyy-MM-dd")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("主题") String theme,
                                      @RequestParam(required = false) @ApiParam("内容") String content,
                                      @RequestParam(required = false) @ApiParam("主办方") String sponsor,
                                      @RequestParam(required = false) @ApiParam("是否接受报名") Boolean accept,
                                      @RequestParam(required = false) @ApiParam("时间") @DateTimeFormat(pattern = "yyyy-MM-dd") Date time,
                                      @RequestParam(required = false) @ApiParam("地点") String place)
    {

        Perform perform = new Perform()
                .setUserId(userId)
                .setTheme(theme)
                .setContent(content)
                .setSponsor(sponsor)
                .setAccept(accept)
                .setTime(time)
                .setPlace(place);
        performService.updateByIds(perform, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除演出", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        performService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
