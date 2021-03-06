//package com.zuoyue.weiyang.velocity;
//
//import com.zuoyue.weiyang.shiro.JwtPlayload;
//import com.zuoyue.weiyang.shiro.ShiroUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class VelocityShiro {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 是否有该权限
//     * @param permission 权限标识
//     * @return true：是 false：否
//     */
//    public boolean hasPermission(String permission) {
//        logger.info(permission);
//        Subject subject = SecurityUtils.getSubject();
//        return subject != null && subject.isPermitted(permission);
//    }
//
//    /**
//     * 是否拥有该权限
//     * @param permission 权限标识
//     * @return true：是 false：否
//     */
//    public static boolean hasPermissionInMethod(String permission) {
//        Subject subject = SecurityUtils.getSubject();
//        return subject != null && subject.isPermitted(permission);
//    }
//
//    public static boolean hasRoles(String... roles) {
//        return ShiroUtils.hasRoles(roles);
//    }
//
//    public static JwtPlayload principal() {
//        return ShiroUtils.getJwtPlayload();
//    }
//}
