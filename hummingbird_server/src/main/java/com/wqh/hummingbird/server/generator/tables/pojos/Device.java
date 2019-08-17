/*
 * This file is generated by jOOQ.
 */
package com.wqh.hummingbird.server.generator.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;

import org.jooq.types.UShort;


/**
 * 设备表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Device implements Serializable {

    private static final long serialVersionUID = 631409545;

    private Long    id;
    private UShort  typeId;
    private String  code;
    private String  name;
    private Boolean online;

    public Device() {}

    public Device(Device value) {
        this.id = value.id;
        this.typeId = value.typeId;
        this.code = value.code;
        this.name = value.name;
        this.online = value.online;
    }

    public Device(
        Long    id,
        UShort  typeId,
        String  code,
        String  name,
        Boolean online
    ) {
        this.id = id;
        this.typeId = typeId;
        this.code = code;
        this.name = name;
        this.online = online;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UShort getTypeId() {
        return this.typeId;
    }

    public void setTypeId(UShort typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnline() {
        return this.online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Device (");

        sb.append(id);
        sb.append(", ").append(typeId);
        sb.append(", ").append(code);
        sb.append(", ").append(name);
        sb.append(", ").append(online);

        sb.append(")");
        return sb.toString();
    }
}
