package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.Department;
import com.zuoyue.weiyang.bean.Member;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
@Api(tags = "成员接口")
public class MemberController extends BaseApiController {

    @Autowired
    MemberService memberService;

    @PostMapping("/register")
    @ApiOperation(value = "添加成员", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "学生会id", required = true) Long studentUnionId,
                                        @RequestParam @ApiParam(value = "部门id", required = true) Long departmentId)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (studentUnionId == null) return onBadResp("学生会id不能为空");
        if (departmentId == null) return onBadResp("部门id不能为空");

        Member member = new Member()
                .setUserId(userId)
                .setStudentUnionId(studentUnionId)
                .setDepartmentId(departmentId);
        memberService.insert(member);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询成员列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(memberService.list(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改成员信息", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("名称") Long studentUnionId,
                                      @RequestParam(required = false) @ApiParam("部门id") Long departmentId)
    {

        Member member = new Member()
                .setUserId(userId)
                .setStudentUnionId(studentUnionId)
                .setDepartmentId(departmentId);
        memberService.updateByIds(member, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除成员", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        memberService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
