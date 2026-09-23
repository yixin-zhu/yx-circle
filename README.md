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
3. 星球与成员模型 + Flyway
4. 促销抢购（Redis Lua）+ RocketMQ + 幂等开通
5. 可观测（TraceId、Micrometer）、Sentinel、Docker Compose
6. 检索与对象存储（Elasticsearch、MinIO）— 视进度选做

## 开发

**环境：** JDK 17，Maven 3.6+，默认激活 `dev` profile。

**IDEA 启动：**

1. **File → Open** 选择本仓库根目录 `yx-circle`（不要只打开上级 `novamall` 文件夹）。
2. 若未识别为 Maven 项目：右键 `pom.xml` → **Add as Maven Project**，等待依赖下载完成。
3. **File → Project Structure → Project SDK** 选 **JDK 17**；**Modules** 里 Language level 17。
4. 运行：右上角选 **YxCircleApplication**（`.run/` 已带配置），或打开 `YxCircleApplication.java` 点左侧绿色运行按钮。

若模块名不是 `yx-circle`：Run Configuration 里把 Module 改成 IDEA 显示的模块名即可。

**本地验证：**

| 检查项 | 地址 |
|--------|------|
| 健康检查 | `GET http://localhost:8080/actuator/health` |
| Ping | `GET http://localhost:8080/api/v1/ping` |
| 业务异常样例 | `GET http://localhost:8080/api/v1/ping/error-demo` |
| API 文档 | `http://localhost:8080/swagger-ui.html` |

**测试：** 在 IDEA 中运行 `src/test/java` 下全部测试，或 `mvn test`。

## 仓库说明

- 主分支：`main`
