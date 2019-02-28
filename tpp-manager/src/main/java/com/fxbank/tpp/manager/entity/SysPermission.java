package com.fxbank.tpp.manager.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_permission")
public class SysPermission {
    /**
     * 权限编号
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 权限名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 资源类型，[menu|button]
     */
    @Column(name = "resource_type")
    private String resourceType;

    /**
     * 资源路径
     */
    @Column(name = "url")
    private String url;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    @Column(name = "permission")
    private String permission;

    /**
     * 权限父编号
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 权限父编号列表
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 是否可用,0:可用，1：不可用
     */
    @Column(name = "available")
    private Byte available;

    /**
     * 菜单图片路径
     */
    @Column(name = "img_url")
    private String imgUrl;
    
    private List<SysRole> roles;
    
    /**
     * 权限编号
     * @return id 权限编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 权限编号
     * @param id 权限编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 权限名称
     * @return name 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 权限名称
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 资源类型，[menu|button]
     * @return resource_type 资源类型，[menu|button]
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * 资源类型，[menu|button]
     * @param resourceType 资源类型，[menu|button]
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 资源路径
     * @return url 资源路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 资源路径
     * @param url 资源路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     * @return permission 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     * @param permission 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 权限父编号
     * @return parent_id 权限父编号
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 权限父编号
     * @param parentId 权限父编号
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 权限父编号列表
     * @return parent_ids 权限父编号列表
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 权限父编号列表
     * @param parentIds 权限父编号列表
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 是否可用,0:可用，1：不可用
     * @return available 是否可用,0:可用，1：不可用
     */
    public Byte getAvailable() {
        return available;
    }

    /**
     * 是否可用,0:可用，1：不可用
     * @param available 是否可用,0:可用，1：不可用
     */
    public void setAvailable(Byte available) {
        this.available = available;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
    
}