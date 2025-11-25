# CQWM Admin Web

Vue 3 + TypeScript SPA that powers the merchant-facing console.  
The initial iteration focuses on the登录页面 which talks to `/api/v1/admin/auth/login`.

## Available scripts

```bash
npm install          # install dependencies
npm run dev          # start Vite dev server (http://localhost:5173)
npm run build        # production build
npm run preview      # preview the production build locally
```

## Environment variables

- `.env.development` → `VITE_API_BASE_URL=http://localhost:8080`
- `.env.production` → change to your deployed backend gateway.

During dev the Vite proxy in `vite.config.ts` also forwards `/api` calls to `localhost:8080`, so you can keep the backend running locally without CORS issues.

## Login page structure

- `src/pages/LoginPage.vue` — UI + form validation + state hints.
- `src/services/http.ts` — Axios instance with base URL + token header.
- `src/services/auth.ts` — Login API wrapper returning the typed payload from the backend.
- `src/store/auth.ts` — Pinia store that keeps the JWT + user profile in `localStorage`.
- `src/router/index.ts` — Currently exposes `/login`, ready to expand with dashboard routes.

The stored JWT uses the key `cqwm_admin_token`, so the backend can later verify the token via the Authorization header automatically inserted by `http.ts`.
