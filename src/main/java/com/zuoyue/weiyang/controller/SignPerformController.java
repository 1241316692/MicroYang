package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.bean.SignPerform;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.SignPerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/signPerform")
@Api(tags = "报名演出接口")
public class SignPerformController extends BaseApiController {

    @Autowired
    SignPerformService signPerformService;

    @PostMapping("/register")
    @ApiOperation(value = "添加报名演出", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "比赛id", required = true) Long performId)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (performId == null) return onBadResp("演出id不能为空");

        SignPerform signPerform = new SignPerform()
                .setUserId(userId)
                .setPerformId(performId)
                .setTime(new Date())
                .setAffirm(false);
        signPerformService.insert(signPerform);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询报名演出列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signPerformService.list(null)));
    }

    @GetMapping("/selectById")
    @ApiOperation(value = "查询user报名演出列表", notes = "")
    public Map<String, Object> selectById(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort,
                                          @RequestParam @ApiParam("userId") Long userId) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signPerformService.selectById(userId)));
    }

    @GetMapping("/listAffirm")
    @ApiOperation(value = "查询报名演出确认列表", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> listAffirm(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signPerformService.selectAffirm(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改报名演出信息", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("演出id") Long performId)
    {

        SignPerform signPerform = new SignPerform()
                .setUserId(userId)
                .setPerformId(performId);
        signPerformService.updateByIds(signPerform, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("/updateAffirm")
    @ApiOperation(value = "确认报名演出信息", notes = "")
    public Map<String, Object> updateAffirm(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                            @RequestParam (required = false) @ApiParam("用户id") Boolean affirm)
    {

        SignPerform signPerform = new SignPerform()
                .setAffirm(affirm);
        signPerformService.updateByIds(signPerform, id);

        return onSuccessRep("确认报名");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除报名演出", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        signPerformService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
