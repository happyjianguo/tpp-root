drop table sys_role_permission;
create table sys_role_permission(
    permission_id integer comment '权限编号',
    role_id integer comment '角色编号' 
)comment '系统角色权限信息登记簿' ;