# CQWM – 外卖点单系统

单仓库中同时维护后端、商家管理端 Web、用户点单小程序 3 个独立包，方便独立启动和联调。

## 仓库结构

```
CQWM/
├── cqwm-backend/     # Spring Boot 单体后端
├── cqwm-admin-web/   # 商家管理端（Vue3 + Vite 建议）
└── cqwm-miniapp/     # 用户点单微信小程序
```

进入每个子目录查看对应的 `README.md`，里面记录了更细的目录约定与下一步动作。

## 推荐的 Git/GitHub 工作流（企业标准）

1. **主干分支**：`master` 保持可发布状态，只通过 Pull Request 合入。
2. **功能/修复分支命名**：`feature/<short-desc>`、`fix/<ticket-id>`、`chore/<task>`，例如 `feature/menu-crud`.
3. **开发步骤**  
   ```bash
   # 首次克隆
   git clone git@github.com:<org>/CQWM.git
   cd CQWM

   # 创建功能分支
   git checkout -b feature/menu-module

   # 在对应包内开发
   cd cqwm-backend
   # ...coding & testing...

   # 提交
   git status
   git add <files>
   git commit -m "feat(menu): init menu module scaffolding"
   git push origin feature/menu-module
   ```
4. **Pull Request 流程**  
   - 在 GitHub 上创建 PR → `master`，填写说明、关联需求或 issue。  
   - 由至少 1 名同事 Code Review，确认通过后 Squash 或 Rebase merge 到 `master`。  
   - CI（如 GitHub Actions）需在合入前通过，包括：后端单测、前端构建、小程序 Lint。
5. **发布与标签**  
   - 每次上线在 `master` 打 `git tag vX.Y.Z`，记录变更日志。  
   - 部署使用对应 tag 或 commit，保证可追溯。

## 当前功能里程碑

- `feature/admin-login` 分支实现了商家后台登录（前后端 + SQL）：详见 `docs/login-feature.md`。
- 后端：`POST /api/v1/admin/auth/login`，JWT 鉴权骨架 + 异常处理。
- 前端：`cqwm-admin-web` 已搭建成 Vue 3 + Pinia + Axios 工程，并完成登录页。

## 下一步建议

1. 在 `cqwm-miniapp` 内用微信开发者工具导入，并补齐页面文件。  
2. 接入统一权限校验（JWT Filter/Interceptor），并逐步拆解菜单、订单等模块。  
3. 配置 CI（GitHub Actions）以自动化构建/测试 3 个子项目。  
4. 规划部署（数据库、后端、前端静态资源、小程序域名备案）。
