package com.zuoyue.weiyang.controller;

import com.github.pagehelper.PageHelper;
import com.zuoyue.weiyang.bean.MyPageInfo;
import com.zuoyue.weiyang.bean.PageParam;
import com.zuoyue.weiyang.bean.User;
import com.zuoyue.weiyang.common.Constant;
import com.zuoyue.weiyang.enums.RoleType;
import com.zuoyue.weiyang.enums.Roles;
import com.zuoyue.weiyang.service.UserService;
import com.zuoyue.weiyang.shiro.JwtFilter;
import com.zuoyue.weiyang.shiro.ShiroUtils;
import com.zuoyue.weiyang.util.Md5Utils;
import com.zuoyue.weiyang.util.RsaHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户接口")
public class UserController extends BaseApiController {

    private final static int MAX_PWD_LENGTH = 32;

    @Autowired
    private UserService userService;

    @PostMapping("/pubkey")
    @ApiOperation(value = "获取公钥", notes = "")
    public Map<String, Object> pubKey(HttpServletRequest request) throws Exception {
        KeyPair keyPair = RsaHelper.generateRSAKeyPair();
        request.getSession().setAttribute(RsaHelper.PRIVATE_KEY, keyPair.getPrivate());
        return onDataResp(RsaHelper.getKeyString(keyPair.getPublic()));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "")
    public Map<String, Object> register(@RequestParam @ApiParam(value = "用户名", required = true) String username,
                                        @RequestParam @ApiParam(value = "学号", required = true) Long studentId,
                                        @RequestParam @ApiParam(value = "性别",required = true) String sex,
                                        @RequestParam @ApiParam(value = "学校", required = true) String school,
                                        @RequestParam @ApiParam(value = "系别", required = true) String systemType,
                                        @RequestParam @ApiParam(value = "班级", required = true) String classes,
                                        @RequestParam @ApiParam(value = "密码", required = true) String password,
                                        HttpServletRequest request)
    {
        User _user = userService.selectByUname(studentId);
        if (_user != null) return onBadResp(studentId + " 已被注册");

        if (StringUtils.isBlank(username)) return onBadResp("用户名不能为空");
        if (studentId == null) return onBadResp("学号不能为空");
        if (StringUtils.isBlank(sex)) return onBadResp("性别不能为空");
        if (StringUtils.isBlank(school)) return onBadResp("学校不能为空");
        if (StringUtils.isBlank(systemType)) return onBadResp("系别不能为空");
        if (StringUtils.isBlank(classes)) return onBadResp("班级不能为空");
        if (StringUtils.isBlank(password)) return onBadResp("密码不能为空");

        if (password.length() > MAX_PWD_LENGTH) return onBadResp("新密码长度超过限制");

        User user = new User()
                .setUsername(username.trim())
                .setStatus(RoleType.GUEST)
                .setStudentId(studentId)
                .setSex(sex.trim())
                .setSchool(school.trim())
                .setSystemType(systemType.trim())
                .setClasses(classes.trim())
                .setPassword(Md5Utils.encrypt(username.trim(), password));
        userService.insert(user);

        return onSuccessRep("注册成功");
    }



    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "")
    public Map<String, Object> login(@RequestParam @ApiParam(value = "用户名", required = true) String username,
                                     @RequestParam @ApiParam(value = "RSA加密后的密码") String password,
                                     HttpServletRequest request, HttpServletResponse response)
    {
        String decryptPwd = password;
//        try {
//            decryptPwd = RsaHelper.decryptData(password, "utf-8", (PrivateKey) request.getSession().getAttribute(RsaHelper.PRIVATE_KEY));
//        } catch (Exception e) {
//
//        }

        if (StringUtils.isBlank(username)) return onBadResp ("用户名不能为空");
        if (StringUtils.isBlank(decryptPwd)) return onBadResp ("密码不能为空");

        User user = userService.selectByUsername(username);
        if (user != null) {
//            if (!user.getActive()) return onBadResp("该账号尚未激活或已被冻结");

            if (user.getPassword().equals(Md5Utils.encrypt(username, decryptPwd))) {
                String jwtToken = Jwts.builder().setId(String.valueOf(user.getId())).setSubject(username.trim())
                        .setExpiration(new Date(System.currentTimeMillis() + Constant.TOKEN_EXP_TIME))
                        .claim("roles", user.getStatus().getRole()).setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, Constant.JWT_SECRET).compact();

                Cookie cookie = new Cookie(JwtFilter.JWT_TOKEN, jwtToken); // 创建新cookie
                cookie.setMaxAge(Constant.TOKEN_MAX_AGE);// 设置存在时间为5分钟
                cookie.setPath("/");//设置作用域
                response.addCookie(cookie);

                Map<String, Object> result = onDataResp(200, "登录成功", user);
                result.put(JwtFilter.JWT_TOKEN, jwtToken);
                return result;
            }
        }


        return onBadResp("账号或密码错误");
    }




//    @GetMapping("/login")
//    @ApiOperation(value = "登录", notes = "")
//    public Map<String, Object> login(@RequestParam @ApiParam(value = "学号", required = true) Long studentid,
//                                     @RequestParam @ApiParam(value = "密码") String password)
//    {
//
//        if (studentid == null) return onBadResp ("学号不能为空");
//        if (StringUtils.isBlank(password)) return onBadResp ("密码不能为空");
//
//        User user = userService.selectByUname(studentid);
//        if (user != null) {
//            if (password.equals(user.getPassword()))
//            {
//                return onSuccessReps(user,"登陆成功");
//            }
//        }
//        return onBadResps(null,"账号或密码错误");
//    }

    @GetMapping("/list")
    @ApiOperation(value = "查询用户列表", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> list(@ModelAttribute PageParam pageParam, @RequestParam(defaultValue = "id desc") @ApiParam("排序") String sort) {
        PageHelper.startPage(pageParam.getPage_num(), pageParam.getPage_size(), sort);
        return onDataResp(new MyPageInfo(userService.list(null)));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Map<String, Object> update(@RequestParam @ApiParam(value = "用户主键", required = true) Long[] id,
                                      @RequestParam(required = false) @ApiParam("身份")     String username,
                                      @RequestParam(required = false) @ApiParam("身份") String status,
                                      @RequestParam(required = false) @ApiParam("学号")    Long studentId,
                                      @RequestParam(required = false) @ApiParam("性别") String sex,
                                      @RequestParam(required = false) @ApiParam("学校") String school,
                                      @RequestParam(required = false) @ApiParam("系别")    String systemType,
                                      @RequestParam(required = false) @ApiParam("班级")    String classes)
    {

        User user = new User()
                .setUsername(username)
                .setStatus(RoleType.GUEST)
                .setStudentId(studentId)
                .setSex(sex)
                .setSchool(school)
                .setSystemType(systemType)
                .setClasses(classes);
        userService.updateByIds(user, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("/update/pwd")
    @ApiOperation(value = "修改用户密码", notes = "")
    public Map<String, Object> updatePwd(@RequestParam @ApiParam(value = "用户主键", required = true) Long id,
                                         @RequestParam @ApiParam(value = "原密码", required = true) String formerpd,
                                         @RequestParam @ApiParam(value = "新密码", required = true) String password)
    {

        if (password == null) return  onBadResp("密码不能为空");

        User user = userService.selectById(id);
        if (user == null || !user.getPassword().equals(formerpd)) return  onBadResp("原密码不正确");

        if (StringUtils.isBlank(password)) return onBadResp("新密码长度必须大于零");
        if (password.length() > MAX_PWD_LENGTH) return onBadResp("新密码长度超过限制");

        User updateUser = new User()
                .setId(id)
                .setPassword(password);
        userService.updateByIds(updateUser, id);

        return onSuccessRep("修改成功");
    }

    @PostMapping("/update/repwd")
    @ApiOperation(value = "重置用户密码", notes = "")
    @RequiresRoles(value = {Roles.ADMIN, Roles.MANAGER}, logical = Logical.OR)
    public Map<String, Object> updateRepwd(@RequestParam @ApiParam(value = "用户主键", required = true) Long id,
                                           HttpServletRequest request)
    {
        String decryptPwd = "123456";

        User user = userService.selectById(id);
        if (user != null) {
            User updateUser = new User()
                    .setId(id)
                    .setPassword(decryptPwd);
            userService.updateByIds(updateUser, id);
        }
        return onSuccessRep("重置成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除用户", notes = "")
    @RequiresRoles(value = {Roles.ADMIN}, logical = Logical.OR)
    public Map<String, Object> delete(@RequestParam @ApiParam(value = "主键", required = true) Long[] id) {
        userService.deleteByIds(id);
        return onSuccessRep("删除成功");
    }

//    @RequestMapping("/logout")
//    @ApiOperation(value = "注销用户", notes = "")
//    public Map<String, Object> logout(HttpSession session){
//        //通过session.invalidata()方法来注销当前的session
//        session.invalidate();
//        return onSuccessRep("注销成功");
//    }

    // 注销登录接口
    @RequestMapping("/logout")
    @ApiOperation(value = "注销用户", notes = "")
    public Map<String, Object> logout() {
        return onSuccessRep("已退出登录");
    }

}
