package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.Match;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/match")
@Api(tags = "比赛接口")
public class MatchController extends BaseApiController {

    @Autowired
    MatchService matchService;

    @PostMapping("/register")
    @ApiOperation(value = "添加比赛", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "主题", required = true) String theme,
                                        @RequestParam @ApiParam(value = "内容", required = true) String content,
                                        @RequestParam @ApiParam(value = "举办方",required = true) String organizerr)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (StringUtils.isBlank(theme)) return onBadResp("主题不能为空");
        if (StringUtils.isBlank(content)) return onBadResp("内容不能为空");
        if (StringUtils.isBlank(organizerr)) return onBadResp("举办方不能为空");

        Match match = new Match()
                .setUserId(userId)
                .setTheme(theme.trim())
                .setContent(content.trim())
                .setOrganizer(organizerr.trim());
        matchService.insert(match);

        return onSuccessRep("注册成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询比赛列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(matchService.list(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改比赛信息", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("主题") String theme,
                                      @RequestParam(required = false) @ApiParam("内容") String content,
                                      @RequestParam(required = false) @ApiParam("举办方") String organizerr)
    {

        Match match = new Match()
                .setUserId(userId)
                .setTheme(theme)
                .setContent(content)
                .setOrganizer(organizerr);
        matchService.updateByIds(match, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除比赛", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        matchService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }
}
