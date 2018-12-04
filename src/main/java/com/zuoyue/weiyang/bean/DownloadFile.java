package com.zuoyue.weiyang.bean;

public class DownloadFile {

    private Long id;
    private String name;
    private String uri;
    private String extra;

    public Long getId() {
        return id;
    }

    public DownloadFile setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DownloadFile setName(String name) {
        this.name = name;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public DownloadFile setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public DownloadFile setExtra(String extra) {
        this.extra = extra;
        return this;
    }
}
