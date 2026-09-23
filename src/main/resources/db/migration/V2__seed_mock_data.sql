-- Mock data: ~100 rows per core table (MySQL 8+ recursive CTE).

INSERT INTO tb_user (phone, nick_name, icon, role)
WITH RECURSIVE seq AS (SELECT 1 AS n
                       UNION ALL
                       SELECT n + 1
                       FROM seq
                       WHERE n < 100)
SELECT CONCAT('139', LPAD(CAST(n AS CHAR), 8, '0')) AS phone,
       CONCAT('learner_', n)                       AS nick_name,
       CONCAT('/avatar/u', n, '.png')               AS icon,
       CASE WHEN n <= 20 THEN 1 ELSE 0 END          AS role
FROM seq;

INSERT INTO tb_circle_category (name, icon, sort)
WITH RECURSIVE seq AS (SELECT 1 AS n
                       UNION ALL
                       SELECT n + 1
                       FROM seq
                       WHERE n < 100)
SELECT CONCAT('领域-', LPAD(CAST(n AS CHAR), 3, '0')) AS name,
       CONCAT('/icons/cat-', n, '.png')               AS icon,
       n                                              AS sort
FROM seq;

INSERT INTO tb_circle (category_id, host_id, name, cover, description, join_price, member_count, status)
WITH RECURSIVE seq AS (SELECT 1 AS n
                       UNION ALL
                       SELECT n + 1
                       FROM seq
                       WHERE n < 100)
SELECT ((n - 1) % 100) + 1                                           AS category_id,
       ((n - 1) % 20) + 1                                            AS host_id,
       CONCAT('知识星球-', LPAD(CAST(n AS CHAR), 3, '0'))            AS name,
       CONCAT('/covers/circle-', n, '.jpg')                          AS cover,
       CONCAT('第 ', n, ' 号星球：专注垂直领域学习与交流。')         AS description,
       ((n % 50) + 1) * 100                                          AS join_price,
       (n * 3) % 97                                                  AS member_count,
       1                                                             AS status
FROM seq;

INSERT INTO tb_circle_member (circle_id, user_id, status, expire_at)
WITH RECURSIVE seq AS (SELECT 1 AS n
                       UNION ALL
                       SELECT n + 1
                       FROM seq
                       WHERE n < 100)
SELECT n                                              AS circle_id,
       n                                              AS user_id,
       1                                              AS status,
       DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 365 DAY)  AS expire_at
FROM seq;

INSERT INTO tb_post (circle_id, user_id, title, content, liked)
WITH RECURSIVE seq AS (SELECT 1 AS n
                       UNION ALL
                       SELECT n + 1
                       FROM seq
                       WHERE n < 100)
SELECT n                                              AS circle_id,
       ((n - 1) % 20) + 1                             AS user_id,
       CONCAT('学习笔记 #', n)                        AS title,
       CONCAT('这是星球 ', n, ' 内的示例帖子内容，用于本地开发与接口联调。') AS content,
       (n * 7) % 120                                  AS liked
FROM seq;
