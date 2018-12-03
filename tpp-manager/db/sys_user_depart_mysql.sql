
DROP TABLE sys_user_depart;
CREATE TABLE sys_user_depart (
  user_id int(11) DEFAULT NULL COMMENT '用户id',
  depart_id int(11) DEFAULT NULL COMMENT '部门id'
)comment '用户部门关联表' ;
