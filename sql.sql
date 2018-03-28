/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : erp

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-05-07 17:57:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
`cId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '客户编号' ,
`zId`  int(11) NOT NULL COMMENT '客户所在市场区域' ,
`cName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称' ,
`cPhone`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户电话' ,
`cWeChat`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户微信号' ,
`cAddress`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户地址' ,
`cStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '客户状态' ,
PRIMARY KEY (`cId`),
FOREIGN KEY (`zId`) REFERENCES `zone` (`zId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `zId` (`zId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=2

;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES ('1', '3', '吴老师', '15273318022', '4888994', '湖南省株洲市1', '1');
COMMIT;

-- ----------------------------
-- Table structure for `destroy`
-- ----------------------------
DROP TABLE IF EXISTS `destroy`;
CREATE TABLE `destroy` (
`dId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '货损ID,注意这里要注意区分两个完全相同盆子的破损' ,
`piId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '花盆编号' ,
`dWay`  int(4) NOT NULL COMMENT '损坏方式' ,
`dTime`  date NOT NULL COMMENT '损坏时间' ,
`dStatus`  int(4) NOT NULL COMMENT '花盆目前状态' ,
PRIMARY KEY (`dId`),
FOREIGN KEY (`piId`) REFERENCES `portinfo` (`piId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `piId2` (`piId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of destroy
-- ----------------------------
BEGIN;
INSERT INTO `destroy` VALUES ('2', 'Mc-52', '0', '2017-04-19', '1');
COMMIT;

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
`eId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '员工编号' ,
`eName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工姓名' ,
`eCard`  char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号码' ,
`ePhone`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '联系方式不详' COMMENT '员工联系方式' ,
`eHiredate`  date NOT NULL COMMENT '员工入职日期' ,
`eStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '员工状态' ,
PRIMARY KEY (`eId`),
UNIQUE INDEX `eCard` (`eCard`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1010

;

-- ----------------------------
-- Records of employee
-- ----------------------------
BEGIN;
INSERT INTO `employee` VALUES ('1000', '周杰伦', '654887199802111101', '13588660322', '2017-04-16', '1'), ('1001', '何洁', '654622199102123212', '18798451132', '2017-04-16', '1'), ('1002', '曾理', '140685198802240075', '15789666666', '2017-04-16', '1'), ('1009', '吴晓飞', '140624198805210011', '18888888888', '2017-04-23', '1');
COMMIT;

-- ----------------------------
-- Table structure for `portclassify`
-- ----------------------------
DROP TABLE IF EXISTS `portclassify`;
CREATE TABLE `portclassify` (
`pcId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '分类编号' ,
`pcClassify`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '花盆分类' ,
`pcStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '花盆分类状态' ,
PRIMARY KEY (`pcId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of portclassify
-- ----------------------------
BEGIN;
INSERT INTO `portclassify` VALUES ('1', '多肉盆25', '1'), ('2', '组合盆', '1'), ('3', '24寸高盆柱子', '1'), ('4', '18寸套三', '1'), ('5', '中盆', '1'), ('6', '小套盆', '1'), ('7', '小三套', '1'), ('8', '3', '0'), ('9', 'd', '1'), ('10', 'e', '1');
COMMIT;

-- ----------------------------
-- Table structure for `portinfo`
-- ----------------------------
DROP TABLE IF EXISTS `portinfo`;
CREATE TABLE `portinfo` (
`piId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '花盆编号，每个花盆唯一，客户提供' ,
`pcId`  int(11) NOT NULL COMMENT '花盆分类编号' ,
`sId`  int(11) NOT NULL COMMENT '花盆所属供应商' ,
`piName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '花盆名称' ,
`piPrice`  decimal(5,1) NULL DEFAULT NULL COMMENT '花盆标价' ,
`piMaking`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '花盆产地' ,
`piNumber`  int(11) NOT NULL COMMENT '花盆数量(总库存)' ,
`piStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '花盆状态，0为下架，1为在售' ,
`piImage`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`piId`),
FOREIGN KEY (`pcId`) REFERENCES `portclassify` (`pcId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`sId`) REFERENCES `supplier` (`sId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `pcId` (`pcId`) USING BTREE ,
INDEX `sId` (`sId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of portinfo
-- ----------------------------
BEGIN;
INSERT INTO `portinfo` VALUES ('1', '5', '2', '333', '333.0', '33', '333', '1', null), ('10', '1', '1', null, null, null, '5', '1', null), ('11', '1', '1', null, null, null, '0', '1', null), ('12', '1', '1', null, null, null, '5', '1', null), ('13', '1', '1', null, null, null, '2', '1', null), ('14', '1', '1', null, null, null, '5', '1', null), ('15', '1', '1', null, null, null, '5', '1', null), ('16', '1', '1', null, null, null, '5', '1', null), ('17', '1', '1', null, null, null, '1', '1', null), ('18', '1', '1', null, null, null, '1', '1', null), ('19', '1', '1', null, null, null, '1', '1', null), ('2', '2', '1', null, null, null, '22', '0', null), ('20', '1', '1', null, null, null, '1', '1', null), ('3', '3', '1', null, null, null, '2', '1', null), ('4', '1', '1', null, null, null, '11', '0', null), ('4444', '1', '1', '4', '4.0', '4', '4', '0', null), ('5', '1', '1', null, null, null, '1', '1', null), ('6', '3', '1', null, null, null, '53', '1', null), ('7', '3', '2', null, null, null, '10', '1', null), ('8', '1', '1', null, null, null, '2', '1', null), ('9', '2', '2', null, null, null, '9', '1', null), ('Mc-51', '2', '1', '高盆', '15.5', '湖南省株洲市', '1003007', '1', null), ('Mc-52', '5', '1', '', '500.0', '', '1501', '1', null), ('Mc-53', '1', '1', '', '50.0', '', '0', '1', null), ('Mc-54', '1', '2', '', '500.0', '', '0', '1', null);
COMMIT;

-- ----------------------------
-- Table structure for `purchase`
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
`pId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '进货单编号' ,
`piId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品Id' ,
`pCount`  int(11) NOT NULL COMMENT '购买数量' ,
`pPrice`  decimal(7,2) NOT NULL COMMENT '单个进价' ,
`pDate`  date NULL DEFAULT NULL COMMENT '进货时间' ,
`pStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '进货单状态' ,
PRIMARY KEY (`pId`),
FOREIGN KEY (`piId`) REFERENCES `portinfo` (`piId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `piId` (`piId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=35

;

-- ----------------------------
-- Records of purchase
-- ----------------------------
BEGIN;
INSERT INTO `purchase` VALUES ('17', 'Mc-53', '500', '50.00', '2017-04-19', '1'), ('18', 'Mc-52', '1000', '50.00', '2017-04-19', '1'), ('19', 'Mc-51', '1500', '50.00', '2017-04-20', '1'), ('20', 'Mc-54', '20', '500.00', '2017-04-20', '1'), ('21', 'Mc-51', '1000', '55.00', '2017-04-27', '1'), ('27', 'Mc-51', '3', '3.00', '2017-05-02', '1'), ('28', 'Mc-51', '3', '3.00', '2017-05-02', '1'), ('29', 'Mc-51', '4', '4.00', '2017-05-02', '1'), ('30', 'Mc-51', '4', '4.00', '2017-05-02', '1'), ('31', '10', '4', '4.00', '2017-05-02', '1'), ('32', '12', '4', '4.00', '2017-05-02', '1'), ('33', '15', '4', '4.00', '2017-05-02', '1'), ('34', '16', '4', '44.00', '2017-05-02', '1');
COMMIT;

-- ----------------------------
-- Table structure for `supplier`
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
`sId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商编号' ,
`sName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商名称' ,
`sLinkman`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商联系人' ,
`sAddress`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商地址' ,
`sPhone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商联系方式' ,
`sBankAc`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商银行账号' ,
`sWeChat`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商微信号' ,
`sStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '供应商状态' ,
PRIMARY KEY (`sId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of supplier
-- ----------------------------
BEGIN;
INSERT INTO `supplier` VALUES ('1', '微软集团亚洲研究院11', '奥巴马', '中国湖南省株洲市', '15273456655', '15687897897987894891', '16589789798798489231', '1'), ('2', '湖南长沙花盆景观', '小宋', '湖南省长沙市岳麓区', '15273456655', '', '', '1');
COMMIT;

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
`uId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号' ,
`eId`  int(11) NULL DEFAULT NULL COMMENT '员工编号' ,
`uName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`uPass`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码' ,
`uRole`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户权限' ,
PRIMARY KEY (`uId`),
FOREIGN KEY (`eId`) REFERENCES `employee` (`eId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `uName` (`uName`) USING BTREE ,
INDEX `eId` (`eId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
BEGIN;
INSERT INTO `userinfo` VALUES ('1', '1000', 'admin', 'admin', '1'), ('2', '1000', 'root', '111', '1'), ('3', '1000', 'root1', '111', '1');
COMMIT;

-- ----------------------------
-- Table structure for `vendition`
-- ----------------------------
DROP TABLE IF EXISTS `vendition`;
CREATE TABLE `vendition` (
`vId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '销售单编号' ,
`cId`  int(11) NOT NULL COMMENT '客户编号' ,
`piId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '花盆编号' ,
`eId`  int(11) NOT NULL COMMENT '销售人编号' ,
`vCount`  int(11) NOT NULL COMMENT '售出花盆数量' ,
`vPrice`  decimal(7,2) NOT NULL COMMENT '单个售价' ,
`vDate`  date NOT NULL COMMENT '售出时间' ,
`vStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '销售单状态' ,
PRIMARY KEY (`vId`),
FOREIGN KEY (`cId`) REFERENCES `customer` (`cId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`eId`) REFERENCES `employee` (`eId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`piId`) REFERENCES `portinfo` (`piId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `cId1` (`cId`) USING BTREE ,
INDEX `piId1` (`piId`) USING BTREE ,
INDEX `eId1` (`eId`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=19

;

-- ----------------------------
-- Records of vendition
-- ----------------------------
BEGIN;
INSERT INTO `vendition` VALUES ('1', '1', 'Mc-51', '1000', '5000', '50000.00', '2017-04-17', '1'), ('2', '1', 'Mc-51', '1001', '1', '2.00', '2017-04-27', '1'), ('3', '1', 'Mc-51', '1002', '100', '500.00', '2017-04-17', '0'), ('4', '1', 'Mc-51', '1000', '50', '50.00', '2017-04-18', '1'), ('5', '1', 'Mc-51', '1000', '50', '50.00', '2017-04-12', '1'), ('6', '1', 'Mc-51', '1000', '1', '444.00', '2017-04-12', '1'), ('7', '1', 'Mc-51', '1000', '0', '50.00', '2017-04-18', '0'), ('16', '1', '1', '1000', '81', '4.00', '2017-04-12', '1'), ('17', '1', 'Mc-51', '1000', '490', '5.00', '2017-04-27', '1'), ('18', '1', '11', '1000', '1996', '3.00', '2017-05-11', '1');
COMMIT;

-- ----------------------------
-- Table structure for `zone`
-- ----------------------------
DROP TABLE IF EXISTS `zone`;
CREATE TABLE `zone` (
`zId`  int(11) NOT NULL AUTO_INCREMENT COMMENT '市场区域编号' ,
`zName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市场名字' ,
`zStatus`  enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '市场状态' ,
PRIMARY KEY (`zId`),
UNIQUE INDEX `zName` (`zName`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of zone
-- ----------------------------
BEGIN;
INSERT INTO `zone` VALUES ('1', '一桥市场', '1'), ('2', '东湖市场', '1'), ('3', '花鸟市场', '1'), ('4', '苗圃市场', '1'), ('5', '零售', '1'), ('6', '散户', '1');
COMMIT;

-- ----------------------------
-- Auto increment value for `customer`
-- ----------------------------
ALTER TABLE `customer` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for `destroy`
-- ----------------------------
ALTER TABLE `destroy` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for `employee`
-- ----------------------------
ALTER TABLE `employee` AUTO_INCREMENT=1010;

-- ----------------------------
-- Auto increment value for `portclassify`
-- ----------------------------
ALTER TABLE `portclassify` AUTO_INCREMENT=11;

-- ----------------------------
-- Auto increment value for `purchase`
-- ----------------------------
ALTER TABLE `purchase` AUTO_INCREMENT=35;

-- ----------------------------
-- Auto increment value for `supplier`
-- ----------------------------
ALTER TABLE `supplier` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for `userinfo`
-- ----------------------------
ALTER TABLE `userinfo` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `vendition`
-- ----------------------------
ALTER TABLE `vendition` AUTO_INCREMENT=19;

-- ----------------------------
-- Auto increment value for `zone`
-- ----------------------------
ALTER TABLE `zone` AUTO_INCREMENT=7;
