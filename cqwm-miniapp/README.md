# CQWM User Mini Program

This package is the customer ordering channel delivered through WeChat Mini Program.

## Current scaffold

```
cqwm-miniapp
├── app.js / app.json / app.wxss        # Standard global entry files
├── config/                             # Environment & routing constants
├── pages/
│   ├── login/                          # 手机号/微信授权登录
│   ├── store-list/                     # 门店列表、搜索
│   ├── store-detail/                   # 门店详情、公告
│   ├── menu/                           # 菜品/套餐列表 & 规格
│   ├── cart/                           # 购物车
│   ├── order-confirm/                  # 收货信息、支付方式选择
│   ├── order-list/                     # 历史订单
│   └── order-detail/                   # 订单跟踪、催单
├── services/                           # 封装 wx.request，统一鉴权
├── utils/                              # 工具方法（格式化、校验）
└── components/                         # 公用自定义组件
```

Each page directory currently only contains a `.gitkeep` so Git tracks the structure—replace them with the `index.js/json/wxml/wxss` quartet when implementing.

## Bootstrapping guide

1. Import `cqwm-miniapp` into the WeChat Developer Tools.
2. Configure the `project.config.json` with the real `appid` and your preferred settings.
3. Point `globalData.apiBaseUrl` in `app.js` to the backend gateway (`cqwm-backend`).
