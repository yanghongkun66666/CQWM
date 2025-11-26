# 员工管理功能开发文档

说明员工管理的需求、接口、前端页面以及本地运行步骤。开发流程：先更新此文档→实现代码→自测→提 PR。

## 分支与流程
- 分支：`feature/employee-management`（当前）。
- 自测：`mvn test`（后端）、`npm run build`（前端）。
- PR 目标：`master`，说明测试结果和主要变更。

## 后端（cqwm-backend）
- 技术栈：Spring Boot 4 + MyBatis。
- 领域模型：`employee/model/Employee`，字段含 `name/username/phone/gender/idNumber/status/lastOperateAt/...`。
- Mapper：`EmployeeMapper` + XML（`src/main/resources/mappers/employee/EmployeeMapper.xml`），支持新增、更新、状态更新、按姓名模糊分页。
- 服务：`EmployeeService` 负责校验与时间戳填充。
- 接口（全部需 JWT 鉴权）：
  - `GET /api/v1/admin/employees`：分页查询，参数 `name`、`page`、`pageSize`。
  - `POST /api/v1/admin/employees`：新增。
  - `PUT /api/v1/admin/employees/{id}`：修改。
  - `PATCH /api/v1/admin/employees/{id}/status`：启用/禁用。
- 数据库：统一 SQL 在 `docs/sql/schema.sql`（含员工表）；执行后确保表存在，默认库 `cqwm`。
- 运行：
  ```bash
  cd cqwm-backend
  mvn spring-boot:run
  ```

## 前端（cqwm-admin-web）
- 页面：`/employees`（`src/pages/EmployeePage.vue`）
  - 功能：姓名搜索、表格展示、分页、添加/修改弹窗、启用/禁用切换。
  - 表单字段：账号、姓名、手机号、性别（单选）、身份证号。
- 服务：`src/services/employees.ts` 封装 CRUD + 状态更新。
- 路由：已在 `src/router/index.ts` 注册 `/employees`，侧边栏高亮。
- 运行：
  ```bash
  cd cqwm-admin-web
  npm install
  npm run dev
  ```

## 测试/验收清单
- 后端：`mvn -f cqwm-backend/pom.xml test`
- 前端：`npm --prefix cqwm-admin-web run build`
- 手动验收：新增/修改/禁用/分页查询，表单校验（手机号 11 位）。
