package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.bean.StudentUnion;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.StudentUnionService;
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
@RequestMapping("/api/studentunion")
@Api(tags = "学生会接口")
public class StudentUnionController extends BaseApiController {

    @Autowired
    StudentUnionService studentUnionService;

    @PostMapping("/register")
    @ApiOperation(value = "添加学生会", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "名称", required = true) String name,
                                        @RequestParam @ApiParam(value = "第几届", required = true) String sessions,
                                        @RequestParam @ApiParam(value = "内容",required = true) String suContent,
                                        @RequestParam @ApiParam(value = "是否院学生会",required = true) Boolean courtYardStUn)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (StringUtils.isBlank(name)) return onBadResp("名称不能为空");
        if (StringUtils.isBlank(sessions)) return onBadResp("第几届不能为空");
        if (StringUtils.isBlank(suContent)) return onBadResp("内容不能为空");
        if (courtYardStUn == null) return onBadResp("是否院学生会");

        StudentUnion studentUnion = new StudentUnion()
                .setUserId(userId)
                .setName(name.trim())
                .setSessions(sessions.trim())
                .setSuContent(suContent.trim())
                .setCourtYardStUn(courtYardStUn);
        studentUnionService.insert(studentUnion);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询学生会列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(studentUnionService.list(null)));
    }

    @GetMapping("/listDp")
    @ApiOperation(value = "查询学生会部门成员列表", notes = "")
    public Map<String, Object> listDp(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(studentUnionService.listDp(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改学生会信息", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("名称") String name,
                                      @RequestParam(required = false) @ApiParam("第几届") String sessions,
                                      @RequestParam(required = false) @ApiParam("内容") String suContent,
                                      @RequestParam(required = false) @ApiParam("是否院学生会") Boolean courtYardStUn)
    {

        StudentUnion studentUnion = new StudentUnion()
                .setUserId(userId)
                .setName(name)
                .setSessions(sessions)
                .setSuContent(suContent)
                .setCourtYardStUn(courtYardStUn);
        studentUnionService.updateByIds(studentUnion, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除学生会", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        studentUnionService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
