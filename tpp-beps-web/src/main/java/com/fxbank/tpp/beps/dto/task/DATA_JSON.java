package com.fxbank.tpp.beps.dto.task;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class DATA_JSON implements Serializable {

    private static final long serialVersionUID = 8332508786672937206L;

    @JSONField(name = "NAME")
    private String name;

    @JSONField(name = "VALUE")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
