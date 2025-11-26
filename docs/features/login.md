# 登录功能开发文档

记录登录功能的设计、接口和本地运行步骤，供后续交付/联调使用。

## 分支与流程
- 对应分支：`feature/admin-login`（已合入 master）。
- 开发流程：先编写/更新此文档 → 按文档落地代码 → 自测（`mvn test`、`npm run build`）→ 提 PR。

## 后端（cqwm-backend）
- 技术栈：Spring Boot 4 + Spring Security（无会话）+ MyBatis。
- 核心组件：
  - JWT：`auth/service/JwtTokenProvider`，签发 `Bearer` token（`security.jwt.*` 配置）。
  - 控制器：`POST /api/v1/admin/auth/login`（`AuthController`）。
  - 业务：`AuthService` 校验账户状态/密码（BCrypt），写入登录时间/IP，返回 token + profile。
  - 过滤器：`JwtAuthenticationFilter` 解析 `Authorization: Bearer`，放入 `SecurityContext`，其余接口需要鉴权。
- 运行：
  ```bash
  cd cqwm-backend
  mvn spring-boot:run
  ```
  配置 `src/main/resources/application.yml` 的数据源；`mvn test` 使用 H2。

## 数据库
- 统一 SQL：`docs/sql/schema.sql`（建库 + admin seed + 员工表）。执行要点：
  1) 创建/使用数据库 `cqwm`
  2) 默认管理员账号 `admin / Admin@123`（BCrypt 哈希已写入）

## 前端（cqwm-admin-web）
- 技术栈：Vue 3 + TS + Pinia + Axios + Vite。
- 关键文件：
  - 登录页：`src/pages/LoginPage.vue`（表单 + 成功后存储 token/profile 并跳转 /dashboard）
  - 状态：`src/store/auth.ts` 持久化 token/profile 到 localStorage
  - API：`src/services/auth.ts` 调 `/api/v1/admin/auth/login`
  - 路由守卫：`src/router/index.ts` 拦截未登录访问受保护路由
- 运行：
  ```bash
  cd cqwm-admin-web
  npm install
  npm run dev
  ```
  Vite 代理 `/api` 到 `http://localhost:8080`，可在 `.env.development` 调整。

## 测试/验收清单
- 后端：`mvn -f cqwm-backend/pom.xml test`
- 前端：`npm --prefix cqwm-admin-web run build`
- 登录接口返回 token，后续请求头自动附带 `Authorization: Bearer <token>`。
- 截图/说明更新到 PR 描述。
