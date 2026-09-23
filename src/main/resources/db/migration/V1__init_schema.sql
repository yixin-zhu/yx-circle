SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS tb_user
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    phone       VARCHAR(11)     NOT NULL COMMENT '手机号',
    nick_name   VARCHAR(64)     NOT NULL COMMENT '昵称',
    icon        VARCHAR(255)             DEFAULT NULL COMMENT '头像',
    role        TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-成员 1-星主 2-管理员',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_phone (phone)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

CREATE TABLE IF NOT EXISTS tb_circle_category
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)     NOT NULL COMMENT '分类名称',
    icon        VARCHAR(255)             DEFAULT NULL COMMENT '图标',
    sort        INT             NOT NULL DEFAULT 0 COMMENT '排序',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='星球领域分类';

CREATE TABLE IF NOT EXISTS tb_circle
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    category_id  BIGINT UNSIGNED NOT NULL COMMENT '分类 id',
    host_id      BIGINT UNSIGNED NOT NULL COMMENT '星主用户 id',
    name         VARCHAR(128)    NOT NULL COMMENT '星球名称',
    cover        VARCHAR(512)             DEFAULT NULL COMMENT '封面',
    description  VARCHAR(1024)            DEFAULT NULL COMMENT '简介',
    join_price   BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '加入价格，单位分',
    member_count INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '成员数',
    status       TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-草稿 1-开放 2-关闭',
    create_time  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_category (category_id),
    KEY idx_host (host_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='知识星球';

CREATE TABLE IF NOT EXISTS tb_circle_member
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    circle_id BIGINT UNSIGNED NOT NULL COMMENT '星球 id',
    user_id   BIGINT UNSIGNED NOT NULL COMMENT '用户 id',
    status    TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '1-有效 2-过期',
    expire_at DATETIME                 DEFAULT NULL COMMENT '到期时间',
    join_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_circle_user (circle_id, user_id),
    KEY idx_user (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='星球成员';

CREATE TABLE IF NOT EXISTS tb_post
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    circle_id   BIGINT UNSIGNED NOT NULL COMMENT '星球 id',
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '作者 id',
    title       VARCHAR(255)    NOT NULL COMMENT '标题',
    content     VARCHAR(2048)   NOT NULL COMMENT '正文',
    liked       INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '点赞数',
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_circle (circle_id),
    KEY idx_user (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='星球帖子';
