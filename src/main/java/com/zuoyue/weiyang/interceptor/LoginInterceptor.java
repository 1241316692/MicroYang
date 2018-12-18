package com.zuoyue.weiyang.interceptor;

import com.zuoyue.weiyang.annotation.CustomRoles;
import com.zuoyue.weiyang.common.Constant;
import com.zuoyue.weiyang.enums.LoginResponseCode;
import com.zuoyue.weiyang.util.ResponseVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("LoginInterceptor: ");
//        System.out.println("handler: " + handler);
        if (handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getMethodAnnotation(CustomRoles.class) != null) {
                request.getRequestDispatcher("/api/authc/unauthc").forward(request, response);
            }
        }
        PrintWriter writer = null;

        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        String uid = request.getHeader("id");
        String id = request.getHeader("id");
//        System.out.print("uid=\n"+uid);
        try{
            final Claims claims = Jwts.parser().setSigningKey(Constant.JWT_SECRET)
                    .parseClaimsJws(token).getBody();
//            System.out.print("jti="+claims.get("jti"));
            System.out.print("token验证成功\n");
            String ids= (String) claims.get("jti");
            if (ids.equals(uid)){
                System.out.print("id验证成功\n");
            }else {
                System.out.print("id验证失败\n");
                return false;
            }
//            System.out.print(claims.toString());
        }catch (Exception e){
            System.out.print("token 验证失败\n"); return false;
        }

        // token不存在
        if (StringUtils.isEmpty(token)) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.builEnumResponseVO(LoginResponseCode.LOGIN_TOKEN_NOT_NULL, false);
            resopnseMessage(response, writer,responseVO);
            return false;
        }

//        RedisLogin redisLogin = new Gson().fromJson(JedisUtils.getJedis().get(id), RedisLogin.class);
//        if (null == redisLogin) {
//            writer = response.getWriter();
//            ResponseVO responseVO = LoginResponseCode.builEnumResponseVO(LoginResponseCode.PESPONSE_CODE_UNLOGIN_ERROR, false);
//            System.out.print("redisLogin="+redisLogin);
//            resopnseMessage(response, writer, responseVO);
//            return false;
//        }

//        if (!StringUtils.equals(token, redisLogin.getToken())) {
////            writer = response.getWriter();
////            ResponseVO responseVO = LoginResponseCode.builEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
////            resopnseMessage(response, writer, responseVO);
////            System.out.print("equals(token, redisLogin.getToken())=null");
////            return false;
////        }
//
//        // 系统时间>有效时间（说明已经超过有效期）
//        if (System.currentTimeMillis() > redisLogin.getRefTime()) {
//            writer = response.getWriter();
//            ResponseVO responseVO = LoginResponseCode.builEnumResponseVO(LoginResponseCode.LOGIN_TIME_EXP, false);
//            resopnseMessage(response, writer, responseVO);
//            return false;
//        }
//
////        验证登录时间
//        redisLogin = new RedisLogin(Long.parseLong(uid), token, System.currentTimeMillis() + 60L * 1000L * 30L);
//        JedisUtils.getJedis().set(uid, new Gson().toJson(redisLogin));


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void resopnseMessage(HttpServletResponse response, PrintWriter out, ResponseVO responseVO) {
        response.setContentType("application/json;charset=utf-8");
        JSONObject result = new JSONObject();
        result.put("result", responseVO);
        out.print(result);
        out.flush();
        out.close();
    }
}
