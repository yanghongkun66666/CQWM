import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';
import LoginPage from '@/pages/LoginPage.vue';
import DashboardPage from '@/pages/DashboardPage.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: { public: true },
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardPage,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('cqwm_admin_token');
  if (!to.meta.requiresAuth || token) {
    next();
  } else {
    next({ name: 'login' });
  }
});

export default router;
