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

Every domain module already contains empty `controller`, `service`, `repository`, `model`, `dto` sub-packages with a `package-info.java` so you can drop implementations straight in following the layered architecture.

## Development checklist

1. Update `pom.xml` dependencies (Spring Web, Validation, Data JPA/MyBatis, Security, Lombok, etc.).
2. Wire the database connection in `src/main/resources/application-*.yml`.
3. Implement `common.exception` → global `@ControllerAdvice`.
4. Implement the `auth` login flow (username/password or SMS + JWT).
5. Expand modules incrementally and cover them with unit/integration tests under `src/test/java`.
