-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.2.14-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 ippse-wxUser 的数据库结构
CREATE DATABASE IF NOT EXISTS `ippse-wxUser` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ippse-wxUser`;

-- 导出  表 ippse-wxUser.certfile 结构
CREATE TABLE IF NOT EXISTS `certfile` (
  `id` varchar(32) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `ctime` datetime(6) NOT NULL,
  `file` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `wxUser` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5t93au1txm5p34txjlovnqa9n` (`wxUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.certfile 的数据：~0 rows (大约)
DELETE FROM `certfile`;
/*!40000 ALTER TABLE `certfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `certfile` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.clientdetails 结构
CREATE TABLE IF NOT EXISTS `clientdetails` (
  `appId` varchar(255) NOT NULL,
  `resourceIds` varchar(255) DEFAULT NULL,
  `appSecret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `grantTypes` varchar(255) DEFAULT NULL,
  `redirectUrl` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.clientdetails 的数据：~0 rows (大约)
DELETE FROM `clientdetails`;
/*!40000 ALTER TABLE `clientdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientdetails` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.follow 结构
CREATE TABLE IF NOT EXISTS `follow` (
  `id` varchar(32) NOT NULL,
  `ctime` datetime(6) DEFAULT NULL,
  `followers` varchar(255) DEFAULT NULL,
  `following` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.follow 的数据：~0 rows (大约)
DELETE FROM `follow`;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.friend 结构
CREATE TABLE IF NOT EXISTS `friend` (
  `id` varchar(32) NOT NULL,
  `me` varchar(32) DEFAULT NULL,
  `other` varchar(32) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ps` varchar(255) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKajrk1n5hahppwab30g4d17nqn` (`other`),
  KEY `FKfqtkgsfkpgxsxjm8ljr0kn5tn` (`me`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.friend 的数据：~0 rows (大约)
DELETE FROM `friend`;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_access_token 结构
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_access_token 的数据：~0 rows (大约)
DELETE FROM `oauth_access_token`;
/*!40000 ALTER TABLE `oauth_access_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_access_token` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_approvals 结构
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_approvals 的数据：~0 rows (大约)
DELETE FROM `oauth_approvals`;
/*!40000 ALTER TABLE `oauth_approvals` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_approvals` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_client_details 结构
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` text DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_client_details 的数据：~2 rows (大约)
DELETE FROM `oauth_client_details`;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES
	('android', 'resource', '$2a$10$eN0AIqubgoSalm8esXjNseh07DC2c06UF1o2KTL/SFMgro.gEDDf6', 'read,write', 'authorization_code,refresh_token,implicit,password,client_credentials', '', '', 3600, 2592000, '{}', 'true'),
	('web', 'resource', '$2a$10$2QwXh8fwj7nukC45fO2af.KoO1iG7HMTfRQgipRQbhtEy1S6gQHEu', 'read,write', 'authorization_code,refresh_token,implicit,password,client_credentials', 'http://localhost:6021/wxUser/login/oauth2/code/oauth', '', 3600, 2592000, '{}', 'true');
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_client_token 结构
CREATE TABLE IF NOT EXISTS `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_client_token 的数据：~0 rows (大约)
DELETE FROM `oauth_client_token`;
/*!40000 ALTER TABLE `oauth_client_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_client_token` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_code 结构
CREATE TABLE IF NOT EXISTS `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_code 的数据：~0 rows (大约)
DELETE FROM `oauth_code`;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.oauth_refresh_token 结构
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.oauth_refresh_token 的数据：~0 rows (大约)
DELETE FROM `oauth_refresh_token`;
/*!40000 ALTER TABLE `oauth_refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_refresh_token` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `id` varchar(32) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `d` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `r` varchar(255) DEFAULT NULL,
  `u` varchar(255) DEFAULT NULL,
  `userid` varchar(32) DEFAULT NULL,
  `w` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.permission 的数据：~0 rows (大约)
DELETE FROM `permission`;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- 导出  表 ippse-wxUser.wxUser 结构
CREATE TABLE IF NOT EXISTS `wxUser` (
  `id` varchar(32) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `bloodtype` varchar(255) DEFAULT NULL,
  `certcode` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `edulevel` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fans` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `follow` int(11) DEFAULT NULL,
  `hobby` varchar(255) DEFAULT NULL,
  `homepage` varchar(255) DEFAULT NULL,
  `hometown` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `marriage` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `posts` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `religion` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `validtime` datetime DEFAULT NULL,
  `verifycode` varchar(255) DEFAULT NULL,
  `verifiedtime` datetime DEFAULT NULL,
  `verifysendtime` datetime DEFAULT NULL,
  `configs` text DEFAULT NULL,
  `roles` text DEFAULT NULL,
  `property` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ippse-wxUser.wxUser 的数据：~1 rows (大约)
DELETE FROM `wxUser`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
