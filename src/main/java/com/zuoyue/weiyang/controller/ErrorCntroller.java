package com.zuoyue.weiyang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/error")
@ApiIgnore
public class ErrorCntroller {

    private static final String BASE_DIR = "error/";

    @RequestMapping("/400")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle1() {
        return BASE_DIR + "400";
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle2() {
        return BASE_DIR + "404";
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle3() {
        return BASE_DIR + "500";
    }
}
