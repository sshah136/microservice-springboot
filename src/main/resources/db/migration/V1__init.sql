CREATE TABLE `t_inventory`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `sku`  varchar(255) DEFAULT NULL,
    `quantity` int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
);