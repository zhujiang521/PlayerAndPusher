package com.zj.play;

/**
 * @ProjectName: Player
 * @Package: com.zj.player
 * @Author: jiang zhu
 * @Date: 2019/12/25 09:03
 */
public class TVBean {

    String name;

    String url;

    public TVBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public TVBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TVBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

