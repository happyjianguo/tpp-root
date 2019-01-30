
DROP TABLE sys_depart;
CREATE TABLE sys_depart (
  id int(11) NOT NULL COMMENT '主键流水id',
  code varchar(5) DEFAULT NULL COMMENT '部门编码',
  name varchar(50) DEFAULT NULL COMMENT '部门名称',
  parent_id int(11) DEFAULT NULL COMMENT '上级部门id',
  parent_ids varchar(120) DEFAULT NULL COMMENT '部门父编号列表',
  available tinyint(1) DEFAULT NULL COMMENT '是否可用,0:可用，1：不可用',
  PRIMARY KEY (id)
) comment '系统部门信息表' ;

