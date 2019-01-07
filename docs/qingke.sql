/*
SQLyog Enterprise - MySQL GUI v8.05 
MySQL - 5.7.18-log : Database - qingke
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`qingke` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `qingke`;

/*Table structure for table `code_message` */

DROP TABLE IF EXISTS `code_message`;

CREATE TABLE `code_message` (
  `msg_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `to_username` varchar(100) DEFAULT NULL COMMENT '开发者微信号',
  `from_username` varchar(100) DEFAULT NULL COMMENT 'openid',
  `create_time` datetime DEFAULT NULL COMMENT '消息创建时间',
  `msg_type` varchar(100) DEFAULT NULL COMMENT '消息类型，event',
  `event_type` varchar(200) DEFAULT NULL COMMENT '事件类型（未关注subscribe,已关注scan）',
  `evnet_key` varchar(100) DEFAULT NULL COMMENT '事件key值，建议只存储场景id',
  `ticket` varchar(200) DEFAULT NULL COMMENT '二维码ticket',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `code_message` */

insert  into `code_message`(`msg_id`,`to_username`,`from_username`,`create_time`,`msg_type`,`event_type`,`evnet_key`,`ticket`) values (1,'gh_e6d5b29ffdc5','oKjXG01wL5ngvD5jQcYXxBlFaqh8','1970-01-18 20:27:40','event','SCAN','1','gQFO8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRjFZZTFCR1JmNmoxMDAwME0wM0MAAgRdCPBbAwQAAAAA'),(2,'gh_e6d5b29ffdc5','oKjXG08xRrkbzx-rCK5sM2g_7cJE','1970-01-18 20:28:28','event','SCAN','1','gQFO8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRjFZZTFCR1JmNmoxMDAwME0wM0MAAgRdCPBbAwQAAAAA'),(3,'gh_e6d5b29ffdc5','oKjXG01wL5ngvD5jQcYXxBlFaqh8','1970-01-18 20:28:56','event','SCAN','1','gQFO8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRjFZZTFCR1JmNmoxMDAwME0wM0MAAgRdCPBbAwQAAAAA'),(4,'gh_e6d5b29ffdc5','oKjXG0_tHkm06Th8We6u5fFcMuhA','1970-01-18 20:28:56','event','SCAN','1','gQFO8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRjFZZTFCR1JmNmoxMDAwME0wM0MAAgRdCPBbAwQAAAAA');

/*Table structure for table `enterprise` */

DROP TABLE IF EXISTS `enterprise`;

CREATE TABLE `enterprise` (
  `et_numb` int(4) NOT NULL AUTO_INCREMENT COMMENT '企业编号',
  `et_sname` varchar(50) DEFAULT NULL COMMENT '企业简称',
  `et_fname` varchar(100) DEFAULT NULL COMMENT '企业全称',
  `et_type` int(4) DEFAULT NULL COMMENT '企业类型',
  `et_address` varchar(200) DEFAULT NULL COMMENT '注册地址',
  `credit_code` varchar(50) DEFAULT NULL COMMENT '统一社会信用代码',
  `reg_capita` varchar(50) DEFAULT NULL COMMENT '注册资本',
  `legal_person` varchar(50) DEFAULT NULL COMMENT '企业法人',
  `et_linkman` varchar(50) DEFAULT NULL COMMENT '企业联系人',
  `et_phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `et_email` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `et_Date` datetime DEFAULT NULL COMMENT '企业成立时间',
  `et_license_url` varchar(200) DEFAULT NULL COMMENT '企业营业执照电子照片',
  `et_status` tinyint(1) DEFAULT '0' COMMENT '合作状态,合作正常0,合作终止1',
  `et_comment` varchar(500) DEFAULT NULL COMMENT '企业备注',
  PRIMARY KEY (`et_numb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `enterprise` */

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `p_numb` int(4) NOT NULL AUTO_INCREMENT COMMENT '项目编号',
  `et_numb` int(4) DEFAULT NULL COMMENT '企业编号',
  `p_name` varchar(50) DEFAULT NULL COMMENT '项目名称',
  `p_type` int(4) DEFAULT '0' COMMENT '项目类型:不过流水0,过流水1',
  `service_charge` decimal(10,0) DEFAULT NULL COMMENT '服务费',
  `open_account` decimal(10,0) DEFAULT NULL COMMENT '开户费',
  `payment_type` int(4) DEFAULT '0' COMMENT '付款方式:预付0,垫付1',
  `p_principal` varchar(50) DEFAULT NULL COMMENT '项目责任人',
  `et_principal` varchar(50) DEFAULT NULL COMMENT '客户负责人',
  `et_phone` varchar(50) DEFAULT NULL COMMENT '客户联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `update_time` datetime DEFAULT NULL COMMENT '状态更新时间',
  `p_status` int(4) DEFAULT NULL COMMENT '项目状态',
  `qk_quantity` int(4) DEFAULT NULL COMMENT '轻客数量',
  `qk_link` int(4) DEFAULT NULL COMMENT '关联轻客数量',
  `p_content` varchar(500) DEFAULT NULL COMMENT '项目内容',
  `p_operator` varchar(50) DEFAULT NULL COMMENT '操作人员',
  `p_code_url` varchar(200) DEFAULT NULL COMMENT '项目二维码本地地址',
  `wx_code_url` varchar(200) DEFAULT NULL COMMENT '微信二维码地址',
  `wx_ticket` varchar(200) DEFAULT NULL COMMENT '微信ticket',
  PRIMARY KEY (`p_numb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project` */

/*Table structure for table `project_qingke` */

DROP TABLE IF EXISTS `project_qingke`;

CREATE TABLE `project_qingke` (
  `p_numb` int(4) DEFAULT NULL COMMENT '项目编号',
  `qk_numb` int(4) DEFAULT NULL COMMENT '轻客编号',
  `p_qk_status` int(4) DEFAULT NULL COMMENT '项目轻客状态',
  `status_updatetime` datetime DEFAULT NULL COMMENT '状态更新时间',
  `focus_time` datetime DEFAULT NULL COMMENT '项目关注时间',
  `sign_url` varchar(200) DEFAULT NULL COMMENT '签名地址',
  `sign_status` int(4) DEFAULT NULL COMMENT '签名验证状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_qingke` */

/*Table structure for table `project_qingke_status` */

DROP TABLE IF EXISTS `project_qingke_status`;

CREATE TABLE `project_qingke_status` (
  `qkstatus_numb` int(4) DEFAULT NULL COMMENT '轻客状态id',
  `qkstatus_name` varchar(20) DEFAULT NULL COMMENT '轻客状态名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_qingke_status` */

insert  into `project_qingke_status`(`qkstatus_numb`,`qkstatus_name`) values (1,'已注册'),(2,'审核中'),(3,'审核通过'),(4,'审核不通过'),(5,'终止');

/*Table structure for table `project_status` */

DROP TABLE IF EXISTS `project_status`;

CREATE TABLE `project_status` (
  `pstatus_numb` int(4) DEFAULT NULL COMMENT '项目状态ID',
  `pstatus_name` varchar(20) DEFAULT NULL COMMENT '项目状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_status` */

insert  into `project_status`(`pstatus_numb`,`pstatus_name`) values (1,'新建'),(2,'待审批'),(3,'审批完成'),(4,'已发布'),(5,'作废'),(6,'撤回'),(7,'已删除');

/*Table structure for table `protocol` */

DROP TABLE IF EXISTS `protocol`;

CREATE TABLE `protocol` (
  `pro_numb` int(4) NOT NULL AUTO_INCREMENT COMMENT '协议编号',
  `p_numb` int(4) DEFAULT NULL COMMENT '项目编号',
  `pro_type` int(4) DEFAULT NULL COMMENT '协议类型:项目外包协议0,个体代办协议1',
  `pro_photo_url` varchar(200) DEFAULT NULL COMMENT '协议图片地址',
  `pro_photo_sort` int(4) DEFAULT NULL COMMENT '协议图片顺序',
  `upload_date` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`pro_numb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `protocol` */

/*Table structure for table `protocoltype` */

DROP TABLE IF EXISTS `protocoltype`;

CREATE TABLE `protocoltype` (
  `protype_numb` int(4) DEFAULT NULL COMMENT '协议类型编号',
  `protype_name` varchar(20) DEFAULT NULL COMMENT '协议类型名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `protocoltype` */

insert  into `protocoltype`(`protype_numb`,`protype_name`) values (1,'项目外包协议'),(2,'个体代办协议'),(3,'平台合作协议'),(4,'轻客用户协议');

/*Table structure for table `qingke` */

DROP TABLE IF EXISTS `qingke`;

CREATE TABLE `qingke` (
  `qk_numb` int(4) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `qk_name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `qk_openId` varchar(50) DEFAULT NULL COMMENT '微信ID',
  `qk_sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户性别男0女1',
  `qk_nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `qk_birth` date DEFAULT NULL COMMENT '出生日期',
  `qk_phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `qk_id` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `qkid_frontphoto_url` varchar(200) DEFAULT NULL COMMENT '正面身份证图片地址',
  `qkid_backphoto_url` varchar(200) DEFAULT NULL COMMENT '背面身份证图片地址',
  `qkid_status` int(4) DEFAULT NULL COMMENT '身份证状态',
  `qkid_address` varchar(200) DEFAULT NULL COMMENT '身份证地址',
  `qkid_validity` varchar(50) DEFAULT NULL COMMENT '身份证有效期',
  `qk_video_url` varchar(200) DEFAULT NULL COMMENT '视频验证地址',
  `qk_video_status` int(4) DEFAULT NULL COMMENT '视频验证状态',
  `qk_sign_url` varchar(200) DEFAULT NULL COMMENT '签名地址',
  `qk_sign_status` int(4) DEFAULT NULL COMMENT '签名验证状态',
  `qk_self_name` varchar(100) DEFAULT NULL COMMENT '个体户名称',
  `qk_license_status` int(4) DEFAULT NULL COMMENT '营业执照状态',
  `qk_license_url` varchar(200) DEFAULT NULL COMMENT '营业执照图片地址',
  `qk_city` varchar(50) DEFAULT NULL COMMENT '所在城市',
  `qk_comment` varchar(200) DEFAULT NULL COMMENT '备注',
  `qk_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`qk_numb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qingke` */

/*Table structure for table `qingke_status` */

DROP TABLE IF EXISTS `qingke_status`;

CREATE TABLE `qingke_status` (
  `qk_status_number` int(4) DEFAULT NULL COMMENT '状态id',
  `qk_status_name` varchar(20) DEFAULT NULL COMMENT '状态name'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qingke_status` */

insert  into `qingke_status`(`qk_status_number`,`qk_status_name`) values (1,'未提交'),(2,'已提交'),(3,'审核通过'),(4,'审核不通过');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(4) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_pwd` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
