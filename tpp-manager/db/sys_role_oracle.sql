drop table SYS_ROLE;
-- Create table
create table SYS_ROLE
(
  ID          NUMBER(11),
  ROLE        VARCHAR2(30 CHAR),
  DESCRIPTION VARCHAR2(120 CHAR),
  AVAILABLE   NUMBER(4)
);
-- Add comments to the table 
comment on table SYS_ROLE
  is '系统角色信息登记簿';
-- Add comments to the columns 
comment on column SYS_ROLE.ID
  is '角色编号';
comment on column SYS_ROLE.ROLE
  is '角色标识程序中判断使用,如admin,这个是唯一的';
comment on column SYS_ROLE.DESCRIPTION
  is '角色描述,UI界面显示使用';
comment on column SYS_ROLE.AVAILABLE
  is '是否可用,如果不可用将不会添加给用户,0:可用，1：不可用';
