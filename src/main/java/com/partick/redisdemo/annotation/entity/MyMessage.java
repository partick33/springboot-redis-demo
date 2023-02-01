package com.partick.redisdemo.annotation.entity;

import java.io.Serializable;

/**
 * @author patrick_peng
 * @description
 * @date 2023-02-01 10:25
 **/
public class MyMessage implements Serializable {

    private String id;

    private String context;

    public MyMessage(String id, String context) {
        this.id = id;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
