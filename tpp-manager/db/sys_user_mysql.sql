drop table sys_user;
create table sys_user(
    id integer comment '主键' ,
    username varchar(60) comment '登录名' ,
    name varchar(120) comment '用户名/昵称' ,
    password varchar(32) comment '登录密码' ,
    salt varchar(32) comment '盐' ,
    state varchar(1) comment '用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定' ,
    CONSTRAINT PK_sys_user PRIMARY KEY (id)
)comment '系统用户信息登记簿' ;
CREATE   INDEX sys_sys_user_index1 ON sys_user (username) ;