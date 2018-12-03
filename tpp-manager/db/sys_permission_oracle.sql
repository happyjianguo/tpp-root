drop table SYS_PERMISSION;
-- Create table
create table SYS_PERMISSION
(
  ID            NUMBER(11),
  NAME          VARCHAR2(30 CHAR),
  RESOURCE_TYPE VARCHAR2(20 CHAR),
  URL           VARCHAR2(120 CHAR),
  PERMISSION    VARCHAR2(30 CHAR),
  PARENT_ID     NUMBER(11),
  PARENT_IDS    VARCHAR2(120 CHAR),
  AVAILABLE     NUMBER(4),
  IMG_URL       VARCHAR2(100 CHAR)
);
-- Add comments to the table 
comment on table SYS_PERMISSION
  is '系统权限信息登记簿';
-- Add comments to the columns 
comment on column SYS_PERMISSION.ID
  is '权限编号';
comment on column SYS_PERMISSION.NAME
  is '权限名称';
comment on column SYS_PERMISSION.RESOURCE_TYPE
  is '资源类型，[menu|button]';
comment on column SYS_PERMISSION.URL
  is '资源路径';
comment on column SYS_PERMISSION.PERMISSION
  is '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view';
comment on column SYS_PERMISSION.PARENT_ID
  is '权限父编号';
comment on column SYS_PERMISSION.PARENT_IDS
  is '权限父编号列表';
comment on column SYS_PERMISSION.AVAILABLE
  is '是否可用,0:可用，1：不可用';
comment on column SYS_PERMISSION.IMG_URL
  is '菜单图片路径';
