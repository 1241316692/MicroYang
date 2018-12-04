package com.zuoyue.weiyang.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamConfig {

    @Value("${param.ffmpegPath}")
    public String ffmpegPath;

    @Value("${param.resourcePath}")
    public String resourcePath;
}
