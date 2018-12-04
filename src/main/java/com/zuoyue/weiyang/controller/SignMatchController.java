package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.bean.SignMatch;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.SignMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/signmatch")
@Api(tags = "报名比赛接口")
public class SignMatchController extends BaseApiController {

    @Autowired
    SignMatchService signMatchService;

    @PostMapping("/register")
    @ApiOperation(value = "添加报名比赛", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "比赛id", required = true) Long matchId)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (matchId == null) return onBadResp("比赛id不能为空");

        SignMatch match = new SignMatch()
                .setUserId(userId)
                .setMatchId(matchId)
                .setTime(new Date())
                .setAffirm(false);
        signMatchService.insert(match);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询报名比赛列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signMatchService.list(null)));
    }

    @GetMapping("/selectById")
    @ApiOperation(value = "查询user报名比赛列表", notes = "")
    public Map<String, Object> selectById(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort,
                                          @RequestParam @ApiParam("userId") Long userId) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signMatchService.selectById(userId)));
    }

    @GetMapping("/listAffirm")
    @ApiOperation(value = "查询报名比赛确认列表", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> listAffirm(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(signMatchService.selectAffirm(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改报名比赛信息", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("比赛id") Long matchId)
    {

        SignMatch match = new SignMatch()
                .setUserId(userId)
                .setMatchId(matchId);
        signMatchService.updateByIds(match, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("/updateAffirm")
    @ApiOperation(value = "确认报名比赛信息", notes = "")
    public Map<String, Object> updateAffirm(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Boolean affirm)
    {

        SignMatch match = new SignMatch()
                .setAffirm(affirm);
        signMatchService.updateByIds(match, id);

        return onSuccessRep("确认报名");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除报名比赛", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        signMatchService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
