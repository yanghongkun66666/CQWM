# Login Feature Blueprint

This document records everything required to bring up the merchant-admin login from any environment and explains the workflow for merging the feature branch.

## Branch & workflow

- Current branch: `feature/admin-login`.
- After local verification, push with `git push -u origin feature/admin-login`.
- Open a PR → `master`, request review, and merge via squash/rebase only after the checks below pass.

## Backend (cqwm-backend)

### Stack & modules
- Spring Boot 4 + Spring Security (stateless) + MyBatis (XML mappers in `src/main/resources/mappers`).
- JWT utilities under `auth/service/JwtTokenProvider`.
- REST endpoint: `POST /api/v1/admin/auth/login`.

### Request / response

```http
POST /api/v1/admin/auth/login HTTP/1.1
Content-Type: application/json

{
  "username": "admin",
  "password": "Admin@123",
  "rememberMe": true
}
```

Response body (`ApiResponse<LoginResponse>`):

```json
{
  "success": true,
  "code": "0",
  "message": "OK",
  "timestamp": "2025-11-25T03:05:00.000Z",
  "data": {
    "token": "eyJhbGciOi...",
    "expiresIn": 604800,
    "profile": {
      "id": 1,
      "username": "admin",
      "fullName": "平台管理员",
      "phone": "13800000000",
      "email": "admin@example.com"
    }
  }
}
```

#### Run locally

```bash
cd cqwm-backend
mvn spring-boot:run
```

Configure `application.yml` with your real MySQL credentials. Tests rely on H2, so they run without MySQL:

```bash
mvn test
```

## Database

SQL schema + seed user are stored in `docs/db/admin_login.sql`.

1. Create database `cqwm`.
2. Execute the file; it creates `cqwm_admin_user` and inserts default account `admin / Admin@123` (BCrypt hash).
3. Update password via `BCryptPasswordEncoder` if needed.

## Frontend (cqwm-admin-web)

- Vue 3 + TypeScript + Pinia + Axios + Vite.
- `src/pages/LoginPage.vue` holds UI & form logic.
- API client sits in `src/services/auth.ts`.
- The JWT & profile persist in Pinia store `src/store/auth.ts`.

Run locally:

```bash
cd cqwm-admin-web
npm install
npm run dev
```

Vite proxies `/api` calls to `http://localhost:8080`. Adjust `.env.development` if the backend runs elsewhere.

## Merge checklist

1. `mvn -f cqwm-backend/pom.xml test`
2. `npm --prefix cqwm-admin-web run build`
3. Database SQL exported (`docs/db/admin_login.sql`)
4. Update PR description with testing notes + screenshots if possible
5. Merge PR into `master`, then clean branch:
   ```bash
   git checkout master
   git pull origin master
   git branch -d feature/admin-login
   git push origin --delete feature/admin-login
   ```
