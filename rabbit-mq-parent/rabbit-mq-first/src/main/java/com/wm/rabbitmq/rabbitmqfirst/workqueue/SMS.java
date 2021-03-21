package com.wm.rabbitmq.rabbitmqfirst.workqueue;

/**
 * @author wangm
 * @title: SMS
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2114:42
 */
public class SMS {
    private String name;

    private String mobile;

    private String dest;

    public SMS(String name, String mobile, String dest) {
        this.name = name;
        this.mobile = mobile;
        this.dest = dest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
