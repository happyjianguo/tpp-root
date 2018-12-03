package com.fxbank.tpp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_depart")
public class SysDepart extends BaseData{

    /**
     * 部门id
     */
    @Id
    @Column(name="id")
    private Integer id;

    /**
     * 部门编码
     */
    @Column(name="code")
    private String code;

    /**
     * 部门名称
     */
    @Column(name="name")
    private String name;

    /**
     * 上级菜单
     */
    @Column(name="parent_id")
    private Integer parentId;

    /**
     * 父编号列表
     */
    @Column(name="parent_ids")
    private String parentIds;

    /**
     * 是否可用,如果不可用将不会添加给用户,0:可用，1：不可用
     */
    @Column(name="available")
    private Byte available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
