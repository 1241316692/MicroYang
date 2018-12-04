package com.zuoyue.weiyang.velocity;

public class VelocityTool {

    public String out() {
        return "velocityTool";
    }

    public String toHtml(String str) {
        return str.replaceAll("\n","<br/>").replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("\\s","&nbsp;");
    }
}
