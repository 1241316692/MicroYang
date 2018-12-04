package com.zuoyue.weiyang.convert;

import com.zuoyue.weiyang.enums.RoleType;
import org.springframework.core.convert.converter.Converter;

public class StringToRoleType implements Converter<String, RoleType> {

    @Override
    public RoleType convert(String source) {
        return RoleType.valueOf(Integer.valueOf(source));
    }
}
