drop table sys_permission;
create table sys_permission(
    id integer comment '权限编号' ,
    name varchar(30) comment '权限名称' ,
    resource_type varchar(20) comment '资源类型，[menu|button]' ,
    url varchar(120) comment '资源路径' ,
    permission varchar(30) comment '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view' ,
    parent_id  integer comment '权限父编号' ,
    parent_ids  varchar(120) comment '权限父编号列表' ,
    available  boolean comment '是否可用,0:可用，1：不可用' ,
    img_url    VARCHAR(100) comment '菜单图片路径' ,
    CONSTRAINT PK_sys_permission PRIMARY KEY (id)
)comment '系统权限信息登记簿' ;