package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.Lecture;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.LectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/lecture")
@Api(tags = "讲座接口")
public class LectureController extends BaseApiController {

    @Autowired
    LectureService lectureService;

    @PostMapping("/register")
    @ApiOperation(value = "添加讲座", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long user_id,
                                        @RequestParam @ApiParam(value = "主题", required = true) String theme,
                                        @RequestParam @ApiParam(value = "内容", required = true) String content,
                                        @RequestParam @ApiParam(value = "讲师",required = true) String lecturer,
                                        @RequestParam @ApiParam(value = "时长", required = true) String duration,
                                        @RequestParam @ApiParam(value = "人数", required = true) Long number)
    {

        if (user_id == null) return onBadResp("user_id不能为空");
        if (StringUtils.isBlank(theme)) return onBadResp("主题不能为空");
        if (StringUtils.isBlank(content)) return onBadResp("内容不能为空");
        if (StringUtils.isBlank(lecturer)) return onBadResp("讲师不能为空");
        if (StringUtils.isBlank(duration)) return onBadResp("时长不能为空");
        if (number == null) return onBadResp("人数不能为空");

        Lecture lecture = new Lecture()
                .setUser_id(user_id)
                .setTheme(theme.trim())
                .setContent(content.trim())
                .setLecturer(lecturer.trim())
                .setTime(new Date())
                .setDuration(duration.trim())
                .setNumber(number);
        lectureService.insert(lecture);

        return onSuccessRep("注册成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询讲座列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(lectureService.list(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改讲座信息", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long user_id,
                                      @RequestParam(required = false) @ApiParam("主题") String theme,
                                      @RequestParam(required = false) @ApiParam("内容") String content,
                                      @RequestParam(required = false) @ApiParam("讲师") String lecturer,
                                      @RequestParam(required = false) @ApiParam("时长") String duration,
                                      @RequestParam(required = false) @ApiParam("人数") Long number)
    {

        Lecture lecture = new Lecture()
                .setUser_id(user_id)
                .setTheme(theme)
                .setContent(content)
                .setLecturer(lecturer)
                .setTime(new Date())
                .setDuration(duration)
                .setNumber(number);
        lectureService.updateByIds(lecture, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除讲座", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        lectureService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }
}
