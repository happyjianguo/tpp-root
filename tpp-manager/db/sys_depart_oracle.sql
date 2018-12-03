drop table SYS_DEPART;
-- Create table
create table SYS_DEPART
(
  ID            NUMBER(11),
  CODE          VARCHAR2(5 CHAR),
  NAME          VARCHAR2(50 CHAR),
  PARENT_ID     NUMBER(11),
  PARENT_IDS    VARCHAR2(120 CHAR),
  AVAILABLE     NUMBER(4)
);
-- Add comments to the table 
comment on table SYS_DEPART
  is '系统部门信息表';
-- Add comments to the columns 
comment on column SYS_DEPART.ID
  is '主键流水id';
comment on column SYS_DEPART.CODE
  is '部门编码';
comment on column SYS_DEPART.NAME
  is '部门名称';
comment on column SYS_DEPART.PARENT_ID
  is '上级部门id';
comment on column SYS_DEPART.PARENT_IDS
  is '部门父编号列表';
comment on column SYS_DEPART.AVAILABLE
  is '是否可用,0:可用，1：不可用';
