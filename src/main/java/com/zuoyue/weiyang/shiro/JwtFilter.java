package com.zuoyue.weiyang.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 基于JWT标准的无状态认证过滤器
 */
public class JwtFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(AccessControlFilter.class);

    public static final String JWT_TOKEN = "authc_token";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (null != getSubject(request, response) && getSubject(request, response).isAuthenticated()) {
            return true; // 已经验证过直接放行
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken((HttpServletRequest) request);
        if (token != null) {
            try {
                Subject subject = getSubject(request, response);
                subject.login(token);
            } catch (AuthenticationException e) {
//                log.error(e.getMessage(), e);
            }
        }
        return true;
    }

    protected AuthenticationToken createToken(HttpServletRequest request) {
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (JWT_TOKEN.equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }
        if (jwt == null) {
            jwt = request.getHeader(JWT_TOKEN);
        }
        if (jwt == null) return null;

        String host = request.getRemoteHost();
        return new JwtToken(jwt, host);
    }
}
