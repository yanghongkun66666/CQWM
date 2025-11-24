# CQWM Admin Web

This package hosts the merchant-facing management console built with Vue 3 + Vite (TypeScript first).  
No implementation code yet—just the agreed scaffold so every module has a clear home when development starts.

```
cqwm-admin-web
├── public/                # Static assets served as-is by Vite
├── src/
│   ├── assets/            # Global styles, icons
│   ├── components/        # Reusable UI components
│   ├── layouts/           # Shell layouts (header/sidebar)
│   ├── pages/             # Concrete route pages (menu, orders, marketing…)
│   ├── router/            # Route defs, guards
│   ├── services/          # API clients (Axios instances per domain)
│   ├── store/             # Pinia stores / global state
│   └── utils/             # Cross-cutting helpers
└── package.json           # (to be generated once we run `npm create vite@latest`)
```

### Suggested next steps

1. From repo root run `cd cqwm-admin-web && npm create vite@latest . -- --template vue-ts`.
2. Replace the generated directories with the placeholders above where appropriate.
3. Keep environment variables (API base URLs, feature flags) under `.env.local` per deployment target.
