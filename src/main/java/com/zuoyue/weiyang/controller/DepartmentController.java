package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.Department;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/department")
@Api(tags = "部门接口")
public class DepartmentController extends BaseApiController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/register")
    @ApiOperation(value = "添加部门", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户id", required = true) Long userId,
                                        @RequestParam @ApiParam(value = "学生会id", required = true) Long studentUnionId,
                                        @RequestParam @ApiParam(value = "部门名称", required = true) String departmentName,
                                        @RequestParam @ApiParam(value = "内容",required = true) String dpContent,
                                        @RequestParam @ApiParam(value = "是否院部门",required = true) Boolean hospitalDepartment,
                                        @RequestParam @ApiParam(value = "系别",required = false) String systemType)
    {

        if (userId == null) return onBadResp("user_id不能为空");
        if (studentUnionId == null) return onBadResp("学生会id不能为空");
        if (StringUtils.isBlank(departmentName)) return onBadResp("部门名称不能为空");
        if (StringUtils.isBlank(dpContent)) return onBadResp("内容不能为空");
        if (hospitalDepartment == null) return onBadResp("是否院部门");
//        if (StringUtils.isBlank(systemType)) return onBadResp("系别不能为空");

        Department department = new Department()
                .setUserId(userId)
                .setStudentUnionId(studentUnionId)
                .setDepartmentName(departmentName.trim())
                .setDpContent(dpContent.trim())
                .setHospitalDepartment(hospitalDepartment)
                .setSystemType(systemType);
        departmentService.insert(department);

        return onSuccessRep("添加成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询学生会列表", notes = "")
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(departmentService.list(null)));
    }

    @GetMapping("/listDepartment")
    @ApiOperation(value = "根据id查询学生会列表及成员", notes = "")
    public Map<String, Object> listDepartment(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort,@RequestParam Long id) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
//        Department department=new Department();
//        department.setId(id);
        return onDataResp(new MyPageInfo(departmentService.selectByMb(id)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改部门信息", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam (required = false) @ApiParam("用户id") Long userId,
                                      @RequestParam(required = false) @ApiParam("名称") Long studentUnionId,
                                      @RequestParam(required = false) @ApiParam("第几届") String departmentName,
                                      @RequestParam(required = false) @ApiParam("内容") String dpContent,
                                      @RequestParam(required = false) @ApiParam("是否院学生会") Boolean hospitalDepartment,
                                      @RequestParam(required = false) @ApiParam("内容") String systemType)
    {

        Department department = new Department()
                .setUserId(userId)
                .setStudentUnionId(studentUnionId)
                .setDepartmentName(departmentName)
                .setDpContent(dpContent)
                .setHospitalDepartment(hospitalDepartment)
                .setSystemType(systemType);
        departmentService.updateByIds(department, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除部门", notes = "")
//    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        departmentService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

}
