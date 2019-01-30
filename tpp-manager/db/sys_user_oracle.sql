drop table SYS_USER;
-- Create table
create table SYS_USER
(
  ID       NUMBER(11),
  USERNAME VARCHAR2(60 CHAR),
  NAME     VARCHAR2(120 CHAR),
  PASSWORD VARCHAR2(32 CHAR),
  SALT     VARCHAR2(32 CHAR),
  STATE    VARCHAR2(1 CHAR)
);
-- Add comments to the table 
comment on table SYS_USER
  is '系统用户信息登记簿';
-- Add comments to the columns 
comment on column SYS_USER.ID
  is '主键';
comment on column SYS_USER.USERNAME
  is '登录名';
comment on column SYS_USER.NAME
  is '用户名/昵称';
comment on column SYS_USER.PASSWORD
  is '登录密码';
comment on column SYS_USER.SALT
  is '盐';
comment on column SYS_USER.STATE
  is '用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定';
create index SYS_SYS_USER_INDEX1 on SYS_USER (USERNAME);