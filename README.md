# yx-circle

付费垂直 **知识社群** 后端：星主创建私密星球，成员付费加入，支持促销名额抢购、异步权益开通、社群内容与学习打卡。

## 产品边界（MVP）

- **星球（Circle）**：领域分类、封面、加入价、成员规模
- **成员（Membership）**：加入订单 → 权益生效 / 过期
- **内容（Post）**：星球内帖子，默认仅成员可见
- **交易（Promo）**：限量促销名额（高并发抢购 + 消息异步开通）
- **打卡（Check-in）**：连续学习/参与统计

## 技术演进（计划）

1. ~~Spring Boot 骨架与统一 API 规范~~（已完成）
2. 用户与 Token 会话
3. ~~Flyway + MySQL 表结构 + MyBatis-Plus + 模拟数据~~（已完成）
4. 星球 / 成员 / 帖子业务 API
5. 促销抢购（Redis Lua）+ RocketMQ + 幂等开通
6. 可观测（TraceId、Micrometer）、Sentinel、Docker Compose
7. 检索与对象存储（Elasticsearch、MinIO）— 视进度选做

## 仓库说明

- 主分支：`main`
