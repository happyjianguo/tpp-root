drop table SYS_ROLE_PERMISSION;
-- Create table
create table SYS_ROLE_PERMISSION
(
  PERMISSION_ID NUMBER(11),
  ROLE_ID       NUMBER(11)
);
-- Add comments to the table 
comment on table SYS_ROLE_PERMISSION
  is '系统角色权限信息登记簿';
-- Add comments to the columns 
comment on column SYS_ROLE_PERMISSION.PERMISSION_ID
  is '权限编号';
comment on column SYS_ROLE_PERMISSION.ROLE_ID
  is '角色编号';
