drop table SYS_USER_ROLE;
-- Create table
create table SYS_USER_ROLE
(
  ROLE_ID NUMBER(11),
  ID      NUMBER(11)
);
-- Add comments to the table 
comment on table SYS_USER_ROLE
  is '系统用户角色信息登记簿';
-- Add comments to the columns 
comment on column SYS_USER_ROLE.ROLE_ID
  is '角色编号';
comment on column SYS_USER_ROLE.ID
  is '用户信息编号';