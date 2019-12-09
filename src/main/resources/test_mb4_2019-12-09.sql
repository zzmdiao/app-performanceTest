# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 10.10.175.136 (MySQL 5.5.5-10.1.25-MariaDB)
# Database: test_mb4
# Generation Time: 2019-12-09 10:11:10 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table device
# ------------------------------------------------------------

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` varchar(100) NOT NULL COMMENT '设备id',
  `name` varchar(100) DEFAULT NULL COMMENT '设备名',
  `system_version` varchar(50) DEFAULT NULL COMMENT '设备系统版本',
  `cpu_info` varchar(50) DEFAULT NULL COMMENT 'cpu信息',
  `mem_size` varchar(50) DEFAULT NULL COMMENT '内存大小：GB',
  `screen_width` int(11) DEFAULT NULL COMMENT '屏幕宽（像素）',
  `screen_height` int(11) DEFAULT NULL COMMENT '屏幕高（像素）',
  `platform` int(4) DEFAULT NULL COMMENT '平台：1.android  2.ios',
  `status` int(4) DEFAULT NULL COMMENT '设备状态：0.离线 1.使用中 2.空闲',
  `last_online_time` datetime DEFAULT NULL COMMENT '最近一次在线时间',
  `last_offline_time` datetime DEFAULT NULL COMMENT '最近一次离线时间',
  `username` varchar(100) DEFAULT NULL COMMENT '使用人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;

INSERT INTO `device` (`id`, `name`, `system_version`, `cpu_info`, `mem_size`, `screen_width`, `screen_height`, `platform`, `status`, `last_online_time`, `last_offline_time`, `username`, `create_time`)
VALUES
	('95AQACQMEBSFB','[Meizu] MX6','7.1','MT6797','3.0 GB',1080,1920,1,2,'2019-12-06 03:54:21',NULL,NULL,'2019-12-06 03:54:00'),
	('9a98c5f70037dbaaff8fa23839f83d3f3aae158f','6p的?','10.3.2','请根据productType：iPhone7,1\n查出相应的信息，补充到device表','请根据productType：iPhone7,1\n查出相应的信息，补充到device表',414,736,2,2,'2019-10-31 04:08:58',NULL,NULL,'2019-10-31 04:08:34');

/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table element_location
# ------------------------------------------------------------

DROP TABLE IF EXISTS `element_location`;

CREATE TABLE `element_location` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '元素名称',
  `value` varchar(1000) NOT NULL COMMENT '元素值',
  `platform` int(4) DEFAULT NULL COMMENT '平台：1.android  2.ios',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `page_name` varchar(255) NOT NULL COMMENT '页面名称',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1正常。0已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元素定位表';

LOCK TABLES `element_location` WRITE;
/*!40000 ALTER TABLE `element_location` DISABLE KEYS */;

INSERT INTO `element_location` (`id`, `name`, `value`, `platform`, `create_time`, `update_time`, `page_name`, `status`)
VALUES
	(1,'退出登录','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'退出登录\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(2,'昨日出借回报','[{\"paramBy\":\"id\",\"paramValue\":\"tvYesterdayEarnings\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(3,'购买页立即投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"join_product_confirm\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(4,'出借记录-散标','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'散标\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(5,'资金流水','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'资金流水\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(6,'奖励劵弹窗-知道了按钮','[{\"paramBy\":\"id\",\"paramValue\":\"tv_add_interest_guide_confirm\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(7,'首页tab','[{\"paramBy\":\"id\",\"paramValue\":\"tab1Iv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','首页',1),
	(8,'发现tab','[{\"paramBy\":\"id\",\"paramValue\":\"tab3Iv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','发现',1),
	(9,'资金流水-转让债权','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'转让债权\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(10,'出借记录-整存宝+','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'整存宝+\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(11,'资金流水-回收','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'回收\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(12,'用户名','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'请输入手机号/用户名/邮箱\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(13,'月进宝金额输入框','[{\"paramBy\":\"id\",\"paramValue\":\"etAmount\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(14,'资金流水-充值','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'充值\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(15,'奖励劵','[{\"paramBy\":\"id\",\"paramValue\":\"tvAddInterestNum\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(16,'跳过','[{\"paramBy\":\"id\",\"paramValue\":\"guideStartBtn\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(17,'出借记录-月进宝','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'月进宝\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(18,'注册按钮','[{\"paramBy\":\"id\",\"paramValue\":\"loginBackRegistTv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(19,'查看过期奖励劵','[{\"paramBy\":\"id\",\"paramValue\":\"tv_coupon_new_expired\"}, {\"paramBy\":\"id\",\"paramValue\":\"tv_reward_new_expired\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(20,'重新登录','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'重新登录\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(21,'出借记录-已结束','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'已结束\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(22,'登录按钮','[{\"paramBy\":\"id\",\"paramValue\":\"loginSubmitTv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(23,'三月期立即投资按钮','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'3\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(24,'产品tab','[{\"paramBy\":\"id\",\"paramValue\":\"tab2Iv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(25,'出借记录-转让中','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'转让中\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(26,'金额输入框','[{\"paramBy\":\"id\",\"paramValue\":\"join_product_edit_text\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(27,'月进宝','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'月进宝·月回本息\']\"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'月进宝\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(28,'月进宝立即投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"joinProductConfirm\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(29,'查看过期优惠劵','[{\"paramBy\":\"id\",\"paramValue\":\"tv_coupon_new_expired\"}, {\"paramBy\":\"id\",\"paramValue\":\"tv_reward_new_expired\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(30,'设置按钮','[{\"paramBy\":\"id\",\"paramValue\":\"tvSetting\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(31,'出借记录','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'出借记录\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(32,'散标出借记录期号','[{\"paramBy\":\"id\",\"paramValue\":\"record_invert_item_issue\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(33,'资金流水日期','[{\"paramBy\":\"id\",\"paramValue\":\"tvFlowTime\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(34,'开始使用爱钱进','[{\"paramBy\":\"id\",\"paramValue\":\"guideBottomStartTv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(35,'月进宝立即投资','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'3\']\"}, {\"paramBy\":\"id\",\"paramValue\":\"productItemBtn\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(36,'我的资产','[{\"paramBy\":\"id\",\"paramValue\":\"tvTotalAssets\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(37,'优惠劵','[{\"paramBy\":\"id\",\"paramValue\":\"tvRedBagAmount\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(38,'开机广告','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[contains(@text,\'跳过\')]\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(39,'出借记录-已转让','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'已转让\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(40,'其他登录','[{\"paramBy\":\"id\",\"paramValue\":\"registOtherLoginLl\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(41,'出借记录-爱盈宝','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'爱盈宝\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(42,'累计出借回报','[{\"paramBy\":\"id\",\"paramValue\":\"tvTotalProfit\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(43,'确认按钮','[{\"paramBy\":\"id\",\"paramValue\":\"dialogARightButton\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(44,'资金流水-提现','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'提现\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(45,'购买成功页完成按钮','[{\"paramBy\":\"id\",\"paramValue\":\"submit\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(46,'资金流水-出借','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'出借\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','产品',1),
	(47,'密码','[{\"paramBy\":\"id\",\"paramValue\":\"passwdStatusEt\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(48,'请绘制解锁图案','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'请绘制解锁图案\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(49,'我的tab','[{\"paramBy\":\"id\",\"paramValue\":\"tab4Iv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(50,'注册页提示文案','[{\"paramBy\":\"id\",\"paramValue\":\"registDescTv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(51,'资金流水-冻结','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'冻结\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(52,'取消升级','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'取消\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','首页',1),
	(53,'优惠劵弹窗-知道了按钮','[{\"paramBy\":\"id\",\"paramValue\":\"tv_coupon_guide_confirm\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(54,'忘记手势密码','[{\"paramBy\":\"id\",\"paramValue\":\"gestureForget\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(55,'注册下一步','[{\"paramBy\":\"id\",\"paramValue\":\"registSubmitTv\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','注册登录',1),
	(56,'爱盈宝','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'爱盈宝·到期转让\']\"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'爱盈宝\']\"}]',1,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(57,'弹框确认投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"confirmBuySubmit\"}]',1,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(58,'退出登录','[{\"paramBy\":\"id\",\"paramValue\":\"退出登录\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(59,'昨日出借回报','[{\"paramBy\":\"id\",\"paramValue\":\"昨日出借回报(元)\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(60,'确定替换服务器','[{\"paramBy\":\"pre\",\"paramValue\":\"type = \'XCUIElementTypeButton\' and name ==\'确定替换服务器IP\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(61,'购买页立即投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"立即投资\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(62,'doraemon数据采集按钮','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name CONTAINS  \'数据采集\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(63,'出借记录-散标','[{\"paramBy\":\"id\",\"paramValue\":\"散标\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(64,'资金流水','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name=\'资金流水\'\"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//*[@name=\'资金流水\']\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(65,'奖励劵弹窗-知道了按钮','[{\"paramBy\":\"id\",\"paramValue\":\"知道了\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(66,'首页tab','[{\"paramBy\":\"id\",\"paramValue\":\"首页\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','首页',1),
	(67,'发现tab','[{\"paramBy\":\"id\",\"paramValue\":\"发现\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','发现',1),
	(68,'资金流水-转让债权','[{\"paramBy\":\"id\",\"paramValue\":\"转让债权\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(69,'出借记录-整存宝+','[{\"paramBy\":\"id\",\"paramValue\":\"整存宝+\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(70,'资金流水-回收','[{\"paramBy\":\"id\",\"paramValue\":\"回收\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(71,'用户名','[{\"paramBy\":\"id\",\"paramValue\":\"请输入手机号码/用户名/邮箱\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(72,'月进宝金额输入框','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeTextField\' \"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//XCUIElementTypeTextField\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(73,'doraemon确定按钮','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name == \'确定\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(74,'资金流水-充值','[{\"paramBy\":\"id\",\"paramValue\":\"充值\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(75,'奖励劵','[{\"paramBy\":\"id\",\"paramValue\":\"可用奖励券(张)\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(76,'跳过','[{\"paramBy\":\"id\",\"paramValue\":\"跳过\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(77,'doraemon清除本地数据1','[{\"paramBy\":\"id\",\"paramValue\":\"清除性能数据\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(78,'出借记录-月进宝','[{\"paramBy\":\"id\",\"paramValue\":\"月进宝\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(79,'doraemon自定义','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' AND name == \'自定义\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(80,'查看过期奖励劵','[{\"paramBy\":\"id\",\"paramValue\":\"查看过期奖励券\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(81,'重新登录','[{\"paramBy\":\"id\",\"paramValue\":\"重新登录\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(82,'出借记录-已结束','[{\"paramBy\":\"id\",\"paramValue\":\"已结束\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(83,'登录按钮','[{\"paramBy\":\"id\",\"paramValue\":\"登录\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(84,'环境输入框','[{\"paramBy\":\"pre\",\"paramValue\":\"type = \'XCUIElementTypeTextField\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(85,'三月期立即投资按钮','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' AND name == \'3\'\"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//XCUIElementTypeStaticText[@name=\'3\']\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(86,'doraemon清除本地数据2','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' AND name ENDSWITH[C] \'M\' OR  name ENDSWITH[C] \'kb\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(87,'产品tab','[{\"paramBy\":\"id\",\"paramValue\":\"产品\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(88,'环境','[{\"paramBy\":\"pre\",\"paramValue\":\"type = \'XCUIElementTypeButton\' AND name == \'环境\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(89,'出借记录-转让中','[{\"paramBy\":\"id\",\"paramValue\":\"转让中\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(90,'金额输入框','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeTextField\' \"}, {\"paramBy\":\"xpath\",\"paramValue\":\"//XCUIElementTypeTextField\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(91,'月进宝','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' and name BEGINSWITH \'月进宝\'\"}, {\"paramBy\":\"id\",\"paramValue\":\"月进宝\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(92,'月进宝立即投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"立即投资\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(93,'查看过期优惠劵','[{\"paramBy\":\"id\",\"paramValue\":\"查看过期优惠券\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(94,'设置按钮','[{\"paramBy\":\"id\",\"paramValue\":\"设置\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(95,'笑脸','[{\"paramBy\":\"pre\",\"paramValue\":\"type = \'XCUIElementTypeImage\' AND name ENDSWITH[C] \'weixiao\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(96,'出借记录','[{\"paramBy\":\"id\",\"paramValue\":\"出借记录\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(97,'散标出借记录期号','[{\"paramBy\":\"id\",\"paramValue\":\"I17N03003\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(98,'资金流水日期','[{\"paramBy\":\"xpth\",\"paramValue\":\"//XCUIElementTypeStaticText\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(99,'开始使用爱钱进','[{\"paramBy\":\"id\",\"paramValue\":\"开始使用爱钱进\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(100,'月进宝立即投资','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' and name =\'3\'\"}, {\"paramBy\":\"id\",\"paramValue\":\"立即投资\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(101,'我的资产','[{\"paramBy\":\"id\",\"paramValue\":\"我的资产(元)\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(102,'优惠劵','[{\"paramBy\":\"id\",\"paramValue\":\"可用优惠券(元)\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(103,'开机广告','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name CONTAINS \'跳过\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(104,'出借记录-已转让','[{\"paramBy\":\"id\",\"paramValue\":\"已转让\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(105,'没有更多','[{\"paramBy\":\"id\",\"paramValue\":\"没有更多\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(106,'doraemon悬浮窗','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name == \'doraemon logo@3x\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','环境相关',1),
	(107,'其他登录','[{\"paramBy\":\"id\",\"paramValue\":\"其他登录\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(108,'出借记录-爱盈宝','[{\"paramBy\":\"id\",\"paramValue\":\"爱盈宝\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(109,'累计出借回报','[{\"paramBy\":\"id\",\"paramValue\":\"累计出借回报(元)\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(110,'确认按钮','[{\"paramBy\":\"id\",\"paramValue\":\"确认\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(111,'资金流水-提现','[{\"paramBy\":\"id\",\"paramValue\":\"提现\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(112,'购买成功页完成按钮','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name=\'完成\'\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','产品',1),
	(113,'资金流水-出借','[{\"paramBy\":\"id\",\"paramValue\":\"出借\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(114,'密码','[{\"paramBy\":\"id\",\"paramValue\":\"请输入登录密码\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(115,'请绘制解锁图案','[{\"paramBy\":\"id\",\"paramValue\":\"请绘制解锁图案\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','注册登录',1),
	(116,'我的tab','[{\"paramBy\":\"id\",\"paramValue\":\"我的\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(117,'资金流水-冻结','[{\"paramBy\":\"id\",\"paramValue\":\"冻结\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(118,'取消升级','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeButton\' AND name == \'取消\'\"}]',2,'2019-10-24 06:07:46','2019-10-28 14:42:09','首页',1),
	(119,'优惠劵弹窗-知道了按钮','[{\"paramBy\":\"id\",\"paramValue\":\"知道了\"}]',2,'2019-10-24 06:07:46','2019-10-28 14:42:09','我的',1),
	(120,'忘记手势密码','[{\"paramBy\":\"id\",\"paramValue\":\"忘记手势密码\"}]',2,'2019-10-24 06:07:46','2019-10-28 14:42:09','注册登录',1),
	(121,'爱盈宝','[{\"paramBy\":\"pre\",\"paramValue\":\"type == \'XCUIElementTypeStaticText\' and name BEGINSWITH \'爱盈宝\'\"}, {\"paramBy\":\"id\",\"paramValue\":\"爱盈宝\"}]',2,'2019-10-24 06:07:46','2019-10-28 14:42:09','产品',1),
	(122,'弹框确认投资按钮','[{\"paramBy\":\"id\",\"paramValue\":\"确认投资\"}]',2,'2019-10-24 06:07:46','2019-10-28 14:42:09','产品',1),
	(123,'android悬浮窗退出','[{\"paramBy\":\"id\",\"paramValue\":\"close\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','环境相关',1),
	(124,'android悬浮窗文件清理','[{\"paramBy\":\"xpath\",\"paramValue\":\"//*[@text=\'文件清理\']\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','环境相关',1),
	(127,'奖励劵已过期tab','[{\"paramBy\":\"id\",\"paramValue\":\"addInterestInvalidExpired\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(128,'奖励劵已过期tab','[{\"paramBy\":\"id\",\"paramValue\":\"已过期\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1),
	(129,'优惠劵已过期tab','[{\"paramBy\":\"id\",\"paramValue\":\"couponInvalidExpired\"}]',1,'2019-10-24 06:07:44','2019-10-28 14:42:09','我的',1),
	(130,'优惠劵已过期tab','[{\"paramBy\":\"id\",\"paramValue\":\"已过期\"}]',2,'2019-10-24 06:07:45','2019-10-28 14:42:09','我的',1);

/*!40000 ALTER TABLE `element_location` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
