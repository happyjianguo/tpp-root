
-- ----------------------------
--  Records of sys_depart
-- ----------------------------
INSERT INTO sys_depart VALUES ('1', '00000', '阜新银行总行', '0', '/0/1/', '0');
INSERT INTO sys_depart VALUES ('2', '01000', '阜新分行', '1', '/0/1/2/', '0');
INSERT INTO sys_depart VALUES ('3', '02000', '沈阳分行', '1', '/0/1/3/', '0');
INSERT INTO sys_depart VALUES ('4', '03000', '大连分行', '1', '/0/1/4/', '0');
INSERT INTO sys_depart VALUES ('5', '04000', '营口分行', '1', '/0/1/5/', '0');
INSERT INTO sys_depart VALUES ('6', '05000', '葫芦岛分行', '1', '/0/1/6/', '0');
INSERT INTO sys_depart VALUES ('7', '06000', '盘锦分行', '1', '/0/1/7/', '0');
INSERT INTO sys_depart VALUES ('8', '07000', '抚顺分行', '1', '/0/1/8/', '0');
COMMIT;

-- ----------------------------
--  Records of sys_permission
-- ----------------------------
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('1', '系统管理', 'menu', 'userInfo/userList', 'userInfo:view', '0', '0', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('2', '用户管理', 'menu', 'user/list', 'userInfo:view', '1', '0/1', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('3', '角色管理', 'menu', 'role/list', 'roleInfo:view', '1', '0/1', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('4', '菜单管理', 'menu', 'permission/list', '2222', '1', '0/1', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('5', '部门管理', 'menu', 'depart/list', 'depart:view', '1', '0/1', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('8', '公积金业务操作', 'menu', '442', '442', '0', '0', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('9', '签到签退', 'menu', 'paf/loginOut', '7', '8', '0/8', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('10', '签约账户通知', 'menu', 'paf/list', 'paf:acclist', '8', '0/8', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('11', 'redis刷新', 'menu', 'config/redis_refresh', 'redisRefresh:view', '1', '0/1', '0', 'ui/images/image1.jpg');
INSERT INTO sys_permission (id, name, resource_type, url, permission, parent_id, parent_ids, available, img_url) VALUES ('12', '账户变动通知', 'menu', 'paf/accMstList', 'pafaccmstlist:view', '8', '0/8', '0', 'ui/images/image1.jpg');
COMMIT;

-- ----------------------------
--  Records of sys_role
-- ----------------------------
INSERT INTO sys_role (id, role, description, available) VALUES ('1', 'admin', '管理员', '0');
COMMIT;

-- ----------------------------
--  Records of sys_role_permission
-- ----------------------------
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('1', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('2', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('3', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('4', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('5', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('11', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('8', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('9', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('10', '1');
INSERT INTO sys_role_permission (permission_id, role_id) VALUES ('12', '1');
COMMIT;

-- ----------------------------
--  Records of sys_user
-- ----------------------------
INSERT INTO sys_user (id, username, name, password, salt, state) VALUES ('1', 'admin', '管理员', '5ba169277ed4e081ab1204644fae5512', '8d78869f470951332959580424d4bf4f', '0');
COMMIT;

-- ----------------------------
--  Records of sys_user_depart
-- ----------------------------
INSERT INTO sys_user_depart (user_id, depart_id) VALUES ('1', '1');
COMMIT;

-- ----------------------------
--  Records of sys_user_role
-- ----------------------------
INSERT INTO sys_user_role (role_id, id) VALUES ('1', '1');
COMMIT;


