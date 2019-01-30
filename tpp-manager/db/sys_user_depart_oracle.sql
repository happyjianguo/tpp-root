drop table SYS_USER_DEPART;
-- Create table
create table SYS_USER_DEPART
(
  USER_ID       NUMBER(11),
  DEPART_ID       NUMBER(11)
);
-- Add comments to the table 
comment on table SYS_USER_DEPART
  is '用户部门关联表';
-- Add comments to the columns 
comment on column SYS_USER_DEPART.USER_ID
  is '用户id';
comment on column SYS_USER_DEPART.DEPART_ID
  is '部门id';


