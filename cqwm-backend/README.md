# CQWM Backend (Spring Boot)

`cqwm-backend` hosts the monolithic API that serves both the merchant admin SPA and the user mini program.

## Module layout

```
src/main/java/com/kkWithCodex/cqwm
├── CqwmApplication.java           # Spring Boot bootstrap
├── config/                        # Spring beans: datasource, swagger, cors, security, mq…
├── common/
│   ├── exception/                 # Global exception hierarchy + handlers
│   ├── result/                    # API response wrappers, error codes
│   └── util/                      # Utility helpers
├── auth/                          # 登录、Token、权限
├── user/                          # 用户档案、地址簿
├── merchant/                      # 商家/门店配置
├── menu/                          # 菜品、分类、库存
├── cart/                          # 购物车
├── order/                         # 下单、订单状态流转、支付集成
├── delivery/                      # 配送单/骑手对接
├── payment/                       # 支付渠道（微信、支付宝等适配）
└── integration/                   # 第三方服务（短信、地图、风控…）
```

Every module can follow the conventional `controller` → `service` → `mapper` layering used by the new `auth` implementation (MyBatis + XML mappers under `src/main/resources/mappers`).

## Development checklist

1. Configure database credentials in `src/main/resources/application.yml` (MySQL runtime) and reuse the seed SQL in `docs/db/admin_login.sql`.
2. Run `mvn test` to execute the context-load test (H2 in-memory datasource with JWT defaults).
3. `POST /api/v1/admin/auth/login` returns a JWT + admin profile；see `docs/login-feature.md` for the payload specification.
4. When adding new modules, reuse the shared `ApiResponse` + `ErrorCode` + `BusinessException` pattern to keep responses consistent.
