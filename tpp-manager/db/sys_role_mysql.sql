drop table sys_role;
create table sys_role(
    id integer comment '角色编号' ,
    role varchar(30) comment '角色标识程序中判断使用,如"admin",这个是唯一的' ,
    description varchar(120) comment '角色描述,UI界面显示使用' ,
    available  boolean comment '是否可用,如果不可用将不会添加给用户,0:可用，1：不可用' ,
    CONSTRAINT PK_sys_role PRIMARY KEY (id)
)comment '系统角色信息登记簿' ;