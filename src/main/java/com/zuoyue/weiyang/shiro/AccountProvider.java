package com.zuoyue.weiyang.shiro;

import java.util.HashSet;
import java.util.Set;

public class AccountProvider {

    public Set<String> loadRoles(String clientKey) {
        return new HashSet<>();
    }

    public Set<String> loadPermissions(String clientKey) {
        return new HashSet<>();
    }
}
