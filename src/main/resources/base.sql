/*
Navicat MySQL Data Transfer

Source Server         : 47.92.82.18_wt
Source Server Version : 50726
Source Host           : 47.92.82.18:3306
Source Database       : baas

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-24 11:40:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sfb_button
-- ----------------------------
DROP TABLE IF EXISTS `sfb_button`;
CREATE TABLE `sfb_button` (
  `id` varchar(32) NOT NULL,
  `btn_cn_name` varchar(100) DEFAULT NULL,
  `btn_en_name` varchar(100) DEFAULT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `tree_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_button
-- ----------------------------
INSERT INTO `sfb_button` VALUES ('0113871076224146b2cfce102f2536dd', '修改', 'editDic', '2019-10-24 11:37:00', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('11237c03b46f40c388faaba4f60bd5b4', '修改', 'tree_org_edit', '2019-08-13 18:26:26', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '5d7eb9a50e2844a3b640dce55c118686');
INSERT INTO `sfb_button` VALUES ('29eb4b805b914f89a915596f92d62414', '按钮删除', 'deleteButtonInfo', '2019-08-13 18:29:17', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('2d212927561b4b4ca2ebf77760f2471f', '修改', 'editUserInfo', '2019-08-13 18:24:03', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '437f957c7fba450092bae39ee5f80866');
INSERT INTO `sfb_button` VALUES ('478d0418979d498d9f7d74336ca45a3d', '新增', 'addUserInfo', '2019-08-13 18:23:57', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '437f957c7fba450092bae39ee5f80866');
INSERT INTO `sfb_button` VALUES ('574f02547084416f933dfd22bd8ff2df', '删除', 'tree_org_delete', '2019-08-13 18:26:34', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '5d7eb9a50e2844a3b640dce55c118686');
INSERT INTO `sfb_button` VALUES ('5765aef4ab0846dba2413154a3f075ac', '用户按钮2', '2', '2019-08-13 16:05:54', '2', '0', null, null, null, '253264e0c08a4aadb24fb257bbba8d8d');
INSERT INTO `sfb_button` VALUES ('6006663b43d4409eb6413e1445e4a4ac', '机构及角色分配', 'orgRoleDis', '2019-08-13 18:24:20', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '437f957c7fba450092bae39ee5f80866');
INSERT INTO `sfb_button` VALUES ('6143fd26b6b24ec787bc1864845e7a07', '修改', 'tree_menu_edit', '2019-08-13 18:28:21', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('64ee19083f3b4bc9b9b316533433b082', '按钮查询', 'searchButton', '2019-08-13 18:28:55', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('7b733c8f56da4348aa104dca17d11ebb', '查询', 'searchRole', '2019-08-13 18:25:00', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');
INSERT INTO `sfb_button` VALUES ('835428f0b195478e92d9d6739073dadc', '按钮管理', 'tree_button', '2019-08-13 18:28:36', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('8dad550c22f9477a956c5496e6a7cbc0', '删除', 'deleteUserInfo', '2019-08-13 18:24:10', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '437f957c7fba450092bae39ee5f80866');
INSERT INTO `sfb_button` VALUES ('901cbbac1232484eb23a7eb1469e76a8', '按钮新增', 'addButtonInfo', '2019-08-13 18:29:02', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('91bb0f528c564a358e3515728f5ac436', '按钮分配', 'btnDistribute', '2019-08-13 18:25:41', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');
INSERT INTO `sfb_button` VALUES ('a3ee5592be9a4571889d9b7f30120ca0', '新增', 'tree_menu_add', '2019-08-13 18:28:13', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('a84dca2701c2419aac2159af972dde10', '菜单按钮1', 'sdfsdf', '2019-08-13 16:06:07', '2', '0', null, null, null, 'f94917ada87d4a89a8b9d57f4529ba84');
INSERT INTO `sfb_button` VALUES ('a89b512430194cfc86978c312c4ef0b8', '删除', 'deleteDic', '2019-10-24 11:37:06', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('b3ff4393b34a4d97bbd174617455a495', '删除', 'tree_menu_delete', '2019-08-13 18:28:28', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('b8b22246cf944ff1961faba2b6dc038a', '修改', 'editRoleInfo', '2019-08-13 18:25:17', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');
INSERT INTO `sfb_button` VALUES ('bcfec1d7a45940d0a73cd5e8891d22f1', '查询', 'defaultUserInfo', '2019-08-13 18:23:49', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '437f957c7fba450092bae39ee5f80866');
INSERT INTO `sfb_button` VALUES ('bfb9826e6b544e09906231c8513351bd', '删除', 'deleteRoleInfo', '2019-08-13 18:25:25', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');
INSERT INTO `sfb_button` VALUES ('c292f7767dfa4ece95025abf54f9f7b4', '菜单按钮2', 'sdfasdfadsfa', '2019-08-13 16:06:12', '2', '0', null, null, null, 'f94917ada87d4a89a8b9d57f4529ba84');
INSERT INTO `sfb_button` VALUES ('c77e8383a0a64c5681bb9f49de7f71ec', '删除', 'deleteDicItem', '2019-10-24 11:37:34', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('cae6e151f79d4708af13acf89094391d', '用户按钮1', 'sdf', '2019-08-13 16:05:47', '2', '0', null, null, null, '253264e0c08a4aadb24fb257bbba8d8d');
INSERT INTO `sfb_button` VALUES ('de3d944087824929977c3c139e8cdd02', '新增', 'addDicItem', '2019-10-24 11:37:17', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('e021f34eb366467ab49bc94133e609d8', '菜单分配', 'menuDistribute', '2019-08-13 18:25:34', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');
INSERT INTO `sfb_button` VALUES ('e3502fc790054104bb0e17db6af6aaf2', '新增', 'addDic', '2019-10-24 11:36:53', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('e3b4618603104f7086602a9d4d1f3f90', '按钮修改', 'editButtonInfo', '2019-08-13 18:29:09', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a');
INSERT INTO `sfb_button` VALUES ('ece754bf42204649994841baf5ec73a8', '新增', 'tree_org_add', '2019-08-13 18:26:18', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '5d7eb9a50e2844a3b640dce55c118686');
INSERT INTO `sfb_button` VALUES ('ed8d7c483175490dbc38d6bd12fc20d9', '查询', 'defaultDic', '2019-10-24 11:36:46', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('fabf6691d2a847afb15c135b44a6fe17', '修改', 'editDicItem', '2019-10-24 11:37:27', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617');
INSERT INTO `sfb_button` VALUES ('fcfcbe5c783e4d6aae53b8d6870bdc44', '新增', 'addRoleInfo', '2019-08-13 18:25:08', '058ef28c416d453b93db89b43b07653d', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef');

-- ----------------------------
-- Table structure for sfb_dic
-- ----------------------------
DROP TABLE IF EXISTS `sfb_dic`;
CREATE TABLE `sfb_dic` (
  `id` varchar(32) NOT NULL,
  `cn_name` varchar(100) DEFAULT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `en_name` varchar(50) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_dic
-- ----------------------------

-- ----------------------------
-- Table structure for sfb_dic_detail
-- ----------------------------
DROP TABLE IF EXISTS `sfb_dic_detail`;
CREATE TABLE `sfb_dic_detail` (
  `id` varchar(32) NOT NULL,
  `content` varchar(150) DEFAULT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `dic_id` varchar(40) DEFAULT NULL,
  `dic_val` varchar(100) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_dic_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sfb_org
-- ----------------------------
DROP TABLE IF EXISTS `sfb_org`;
CREATE TABLE `sfb_org` (
  `id` varchar(32) NOT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `pids` longtext,
  `remark` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_org
-- ----------------------------
INSERT INTO `sfb_org` VALUES ('0', '2019-08-13 15:35:57', '', '0', null, null, null, '所有机构', null, '0', null);

-- ----------------------------
-- Table structure for sfb_resource
-- ----------------------------
DROP TABLE IF EXISTS `sfb_resource`;
CREATE TABLE `sfb_resource` (
  `id` varchar(32) NOT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `resource_type` varchar(1) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_resource
-- ----------------------------
INSERT INTO `sfb_resource` VALUES ('0128ae822b06496aa7f8c0c53203aad2', '2019-08-14 09:40:54', '1', '0', null, null, null, '6143fd26b6b24ec787bc1864845e7a07', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('0e7d9f9151d447859983e2e02d908f89', '2019-08-14 09:40:47', '1', '0', null, null, null, '8dad550c22f9477a956c5496e6a7cbc0', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('0f998799fb4441daa83b262a7e7595f9', '2019-08-14 09:41:05', '1', '0', null, null, null, '91bb0f528c564a358e3515728f5ac436', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('0ff2029f37194e1089aa535a94e80431', '2019-08-14 09:40:54', '1', '0', null, null, null, 'a3ee5592be9a4571889d9b7f30120ca0', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('11f1f7e04b51480faa985e0a0e0a9c56', '2019-08-14 09:40:54', '1', '0', null, null, null, '29eb4b805b914f89a915596f92d62414', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('161eaac154dc452b8ffcdcc929e0f059', '2019-08-14 09:40:47', '1', '0', null, null, null, '6006663b43d4409eb6413e1445e4a4ac', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('16fb83add54a44af9f61b66a7ee39b9a', '2019-08-14 09:40:59', '1', '0', null, null, null, '11237c03b46f40c388faaba4f60bd5b4', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('21c90f5b1a1449dfb550e2910d3c2f4d', '2019-08-14 09:40:39', '1', '0', null, null, null, '415aa0ecf01d4c5d85a6467ead9b02c5', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('34968442ecc04e41b2fcbd94f6ecbc09', '2019-08-14 09:41:05', '1', '0', null, null, null, 'b8b22246cf944ff1961faba2b6dc038a', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('3571c80cb0a142179f67673264e6c733', '2019-08-14 09:40:54', '1', '0', null, null, null, 'b3ff4393b34a4d97bbd174617455a495', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('3d67855870f0465098b1bc94b359a257', '2019-08-14 09:40:59', '1', '0', null, null, null, '574f02547084416f933dfd22bd8ff2df', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('42346d0aaab9402caeb1b74efa8cc5ed', '2019-10-24 11:37:45', '1', '0', null, null, null, '5a3c5abb17544221ba645a32fbebd617', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('4d22d13ca1ce455c818a8c0c269ce5d2', '2019-10-24 11:37:52', '1', '0', null, null, null, 'de3d944087824929977c3c139e8cdd02', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('4e3837c80ccb4795a3912bfabdbd6f77', '2019-10-24 11:37:52', '1', '0', null, null, null, 'ed8d7c483175490dbc38d6bd12fc20d9', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('52171ea68e994538b92612a9e6889cb5', '2019-08-14 09:40:47', '1', '0', null, null, null, '478d0418979d498d9f7d74336ca45a3d', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('5cd0ff1056b444cca49c7bdeecd56e6b', '2019-08-14 09:40:54', '1', '0', null, null, null, '901cbbac1232484eb23a7eb1469e76a8', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('71b162ee1d624a5fb4be54d7183e9299', '2019-08-14 09:40:39', '1', '0', null, null, null, '7a64b87732ea4b7cb998c66de53f193a', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('74c1c7ea4eeb4408bfd1794c2b812f10', '2019-08-14 09:40:39', '1', '0', null, null, null, '5d7eb9a50e2844a3b640dce55c118686', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('7aa62ea4b1e9481a8d18e8a9d2da8991', '2019-08-14 09:40:39', '1', '0', null, null, null, '437f957c7fba450092bae39ee5f80866', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('7feb5d3739c24a918fd8c430356f5a9a', '2019-08-14 09:41:05', '1', '0', null, null, null, 'bfb9826e6b544e09906231c8513351bd', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('8998d18b1752404aaf3756deeedfe5c6', '2019-08-14 09:41:05', '1', '0', null, null, null, 'e021f34eb366467ab49bc94133e609d8', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('91664eb7da2244bf9876ff93b4a7e2b9', '2019-08-14 09:40:47', '1', '0', null, null, null, 'bcfec1d7a45940d0a73cd5e8891d22f1', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('9b58eb98f79e4f04bb060a41ab60503c', '2019-08-14 09:40:54', '1', '0', null, null, null, '64ee19083f3b4bc9b9b316533433b082', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('9f690c8ac1eb4611b5c202c87e716a56', '2019-10-24 11:37:52', '1', '0', null, null, null, 'e3502fc790054104bb0e17db6af6aaf2', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('a28546effa6d493d946bb6b020aa280c', '2019-10-24 11:37:52', '1', '0', null, null, null, 'c77e8383a0a64c5681bb9f49de7f71ec', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('aabc9915e3344b3bb1901dcc87173ebe', '2019-08-14 09:41:05', '1', '0', null, null, null, '7b733c8f56da4348aa104dca17d11ebb', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('ba34a1947c5e4becb3cbaca8c16d603d', '2019-08-14 09:41:05', '1', '0', null, null, null, 'fcfcbe5c783e4d6aae53b8d6870bdc44', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('cbdf0b1e94444743b68800b0bf462da2', '2019-08-14 09:40:54', '1', '0', null, null, null, '835428f0b195478e92d9d6739073dadc', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('d2d5587e658f4c968d07de9307ed1747', '2019-08-14 09:40:47', '1', '0', null, null, null, '2d212927561b4b4ca2ebf77760f2471f', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('ed58e65b9dc84e769516ddd3788ebb33', '2019-10-24 11:37:52', '1', '0', null, null, null, '0113871076224146b2cfce102f2536dd', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('f645904331e24e8abc725404b465c409', '2019-08-14 09:40:54', '1', '0', null, null, null, 'e3b4618603104f7086602a9d4d1f3f90', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('fa5bd0f8340843bc933fc22891f18a7c', '2019-08-14 09:40:39', '1', '0', null, null, null, '8ce33e9de6054af6a51c42743f4a99ef', '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('fbf80953bcdd419cb83a4212153844b2', '2019-08-14 09:40:59', '1', '0', null, null, null, 'ece754bf42204649994841baf5ec73a8', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('fc086554b2874aabab395bd3ea1683fe', '2019-10-24 11:37:52', '1', '0', null, null, null, 'fabf6691d2a847afb15c135b44a6fe17', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');
INSERT INTO `sfb_resource` VALUES ('ff573cc0f22e40708b17c6c2fe64f283', '2019-10-24 11:37:52', '1', '0', null, null, null, 'a89b512430194cfc86978c312c4ef0b8', '1', 'a17eb2b1804d416dbdc17b4e0058ae7b');

-- ----------------------------
-- Table structure for sfb_role
-- ----------------------------
DROP TABLE IF EXISTS `sfb_role`;
CREATE TABLE `sfb_role` (
  `id` varchar(32) NOT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `org_id` varchar(32) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_role
-- ----------------------------
INSERT INTO `sfb_role` VALUES ('a17eb2b1804d416dbdc17b4e0058ae7b', '2019-08-13 18:30:05', '1', '0', null, null, '超级管理员', '0', '超级管理员');

-- ----------------------------
-- Table structure for sfb_tree
-- ----------------------------
DROP TABLE IF EXISTS `sfb_tree`;
CREATE TABLE `sfb_tree` (
  `id` varchar(32) NOT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `icon_skin` varchar(100) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `pids` longtext,
  `remark` varchar(20) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_tree
-- ----------------------------
INSERT INTO `sfb_tree` VALUES ('253264e0c08a4aadb24fb257bbba8d8d', '2019-08-13 15:37:24', '1', '', '1', null, null, null, '用户管理', '', '253264e0c08a4aadb24fb257bbba8d8d', '1', '');
INSERT INTO `sfb_tree` VALUES ('415aa0ecf01d4c5d85a6467ead9b02c5', '2019-08-13 18:22:39', '058ef28c416d453b93db89b43b07653d', 'lnr lnr-home', '0', null, null, null, '系统管理', '', '415aa0ecf01d4c5d85a6467ead9b02c5', '1', '');
INSERT INTO `sfb_tree` VALUES ('437f957c7fba450092bae39ee5f80866', '2019-08-13 18:22:54', '058ef28c416d453b93db89b43b07653d', '', '0', null, null, null, '用户管理', '415aa0ecf01d4c5d85a6467ead9b02c5', '415aa0ecf01d4c5d85a6467ead9b02c5,437f957c7fba450092bae39ee5f80866', '1', '/user/loadUser');
INSERT INTO `sfb_tree` VALUES ('5a3c5abb17544221ba645a32fbebd617', '2019-10-24 11:36:21', '1', '', '0', null, null, null, '数据字典', '415aa0ecf01d4c5d85a6467ead9b02c5', '415aa0ecf01d4c5d85a6467ead9b02c5,5a3c5abb17544221ba645a32fbebd617', '5', '/dic/loadDic');
INSERT INTO `sfb_tree` VALUES ('5d7eb9a50e2844a3b640dce55c118686', '2019-08-13 18:23:17', '058ef28c416d453b93db89b43b07653d', '', '0', null, null, null, '机构管理', '415aa0ecf01d4c5d85a6467ead9b02c5', '415aa0ecf01d4c5d85a6467ead9b02c5,5d7eb9a50e2844a3b640dce55c118686', '3', '/org/loadOrg');
INSERT INTO `sfb_tree` VALUES ('7a64b87732ea4b7cb998c66de53f193a', '2019-08-13 18:23:06', '058ef28c416d453b93db89b43b07653d', '', '0', null, null, null, '菜单管理', '415aa0ecf01d4c5d85a6467ead9b02c5', '415aa0ecf01d4c5d85a6467ead9b02c5,7a64b87732ea4b7cb998c66de53f193a', '2', '/tree/loadTree');
INSERT INTO `sfb_tree` VALUES ('8ce33e9de6054af6a51c42743f4a99ef', '2019-08-13 18:23:27', '058ef28c416d453b93db89b43b07653d', '', '0', null, null, null, '角色管理', '415aa0ecf01d4c5d85a6467ead9b02c5', '415aa0ecf01d4c5d85a6467ead9b02c5,8ce33e9de6054af6a51c42743f4a99ef', '4', '/role/loadRole');
INSERT INTO `sfb_tree` VALUES ('c1761b7b51aa43e4ab43cd52c94e3662', '2019-10-24 11:36:02', '1', '', '1', null, null, null, '数据字典', '', 'c1761b7b51aa43e4ab43cd52c94e3662', '5', '/dic/loadDic');
INSERT INTO `sfb_tree` VALUES ('f94917ada87d4a89a8b9d57f4529ba84', '2019-08-13 15:37:31', '1', '', '1', null, null, null, '菜单管理', '', 'f94917ada87d4a89a8b9d57f4529ba84', '2', '');

-- ----------------------------
-- Table structure for sfb_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `sfb_userinfo`;
CREATE TABLE `sfb_userinfo` (
  `id` varchar(32) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `avatar_url` varchar(200) DEFAULT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `is_delete` varchar(1) DEFAULT NULL,
  `last_update_id` varchar(32) DEFAULT NULL,
  `last_update_time` varchar(30) DEFAULT NULL,
  `login_name` varchar(100) DEFAULT NULL,
  `login_pwd` varchar(150) DEFAULT NULL,
  `mark` varchar(300) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `pay_psw` varchar(200) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `private_key` varchar(300) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `org_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sfb_userinfo
-- ----------------------------
INSERT INTO `sfb_userinfo` VALUES ('1', null, null, null, '0', '1', '2019-08-13 18:36:13', 'admin', 'qlGbs3Nc64a5GuvkNEdAQmu6ioDFPR3Y', null, null, null, null, null, null, null, '0', 'a17eb2b1804d416dbdc17b4e0058ae7b');
