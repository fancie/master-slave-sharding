#分别创建database0和database1库, database0是主库, database1是从库
CREATE TABLE `goods_0` (
  `goods_id` bigint NOT NULL,
  `goods_name` varchar(100)  NOT NULL,
  `goods_type` bigint DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB;

CREATE TABLE `goods_1` (
  `goods_id` bigint NOT NULL,
  `goods_name` varchar(100) NOT NULL,
  `goods_type` bigint DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB;
