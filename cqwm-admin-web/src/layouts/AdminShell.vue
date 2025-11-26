<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="brand">
        <div class="logo">🍱</div>
        <div class="brand-text">
          <div class="cn">苍穹外卖</div>
          <div class="en">THE SKY TAKE-OUT</div>
        </div>
        <span class="status">营业中</span>
      </div>
      <nav class="menu">
        <RouterLink class="menu-item" :class="{ active: isActive('/dashboard') }" to="/dashboard">🏠 工作台</RouterLink>
        <div class="menu-item disabled">📊 数据统计</div>
        <div class="menu-item disabled">🧾 订单管理</div>
        <div class="menu-item disabled">🍱 套餐管理</div>
        <div class="menu-item disabled">🍲 菜品管理</div>
        <div class="menu-item disabled">🧩 分类管理</div>
        <RouterLink class="menu-item" :class="{ active: isActive('/employees') }" to="/employees">👥 员工管理</RouterLink>
      </nav>
    </aside>
    <main class="main">
      <header class="topbar">
        <div class="actions">
          <button class="ghost">⟳</button>
          <button class="ghost">⚙️</button>
        </div>
        <div class="user">
          <span class="clock">🕒 营业状态设置</span>
          <div class="user-chip" @click="toggleMenu">
            <span>{{ displayName }}</span>
            <span class="caret">▼</span>
          </div>
          <div v-if="menuOpen" class="dropdown">
            <div class="dropdown-item">{{ displayName }}</div>
            <div class="dropdown-item muted">修改密码</div>
            <div class="dropdown-item danger" @click="handleLogout">退出登录</div>
          </div>
        </div>
      </header>
      <section class="content">
        <slot />
      </section>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, onBeforeUnmount } from 'vue'
import { useAuthStore } from '@/store/auth'
import { useRouter, useRoute, RouterLink } from 'vue-router'
import { logout } from '@/services/auth'

const authStore = useAuthStore()
const router = useRouter()
const route = useRoute()
const menuOpen = ref(false)
const displayName = computed(() => authStore.profile?.fullName || authStore.profile?.username || '管理员')

const toggleMenu = () => {
  menuOpen.value = !menuOpen.value
}

const handleClickOutside = (event: MouseEvent) => {
  const target = event.target as HTMLElement
  if (!target.closest('.user')) {
    menuOpen.value = false
  }
}

const handleLogout = async () => {
  try {
    await logout()
  } catch {
    // ignore errors, we'll still clear local session
  } finally {
    authStore.clear()
    menuOpen.value = false
    router.push({ name: 'login' })
  }
}

onMounted(() => document.addEventListener('click', handleClickOutside))
onBeforeUnmount(() => document.removeEventListener('click', handleClickOutside))

const isActive = (path: string) => route.path.startsWith(path)
</script>

<style scoped>
.layout {
  display: grid;
  grid-template-columns: 240px 1fr;
  min-height: 100vh;
  background: #f5f7fa;
}

.sidebar {
  background: #2b3240;
  color: #d8dde7;
  padding: 20px 12px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.brand {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 10px;
  padding: 12px;
  background: #232a36;
  border-radius: 12px;
}

.logo {
  font-size: 28px;
}

.brand-text .cn {
  font-weight: 700;
  color: #ffd24c;
}

.brand-text .en {
  font-size: 12px;
  color: #9aa3b2;
}

.status {
  background: #ff5d5d;
  color: #fff;
  padding: 4px 8px;
  border-radius: 8px;
  font-size: 12px;
}

.menu {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 14px;
  border-radius: 10px;
  color: #d8dde7;
  text-decoration: none;
  background: transparent;
}

.menu-item:hover,
.menu-item.active {
  background: #1f2632;
  color: #fff;
}

.main {
  display: flex;
  flex-direction: column;
}

.topbar {
  height: 64px;
  background: #f9c21a;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  position: sticky;
  top: 0;
  z-index: 10;
}

.actions {
  display: flex;
  gap: 10px;
}

.ghost {
  border: none;
  background: rgba(255, 255, 255, 0.6);
  padding: 8px 10px;
  border-radius: 10px;
  cursor: pointer;
}

.alerts {
  display: flex;
  align-items: center;
}

.alert {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 10px;
  background: #fff;
  padding: 10px 12px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.alert .icon {
  color: #ff5d5d;
  font-weight: 700;
}

.alert .title {
  font-weight: 700;
}

.alert .desc {
  color: #8a95a6;
  font-size: 13px;
}

.alert .close {
  border: none;
  background: transparent;
  cursor: pointer;
  color: #8a95a6;
}

.content {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.user {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  margin-left: auto;
}

.clock {
  font-size: 13px;
  color: #4a3500;
  background: rgba(0, 0, 0, 0.06);
  padding: 8px 10px;
  border-radius: 10px;
}

.user-chip {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 12px;
  background: #fdf1d5;
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 10px;
  color: #4a3500;
  font-weight: 600;
  cursor: default;
}

.caret {
  font-size: 10px;
  color: #8a7a52;
}

.dropdown {
  position: absolute;
  top: 48px;
  right: 0;
  background: #fff7e6;
  border: 1px solid #e6cf9f;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.12);
  min-width: 140px;
  overflow: hidden;
}

.dropdown-item {
  padding: 10px 12px;
  cursor: pointer;
  color: #4a3500;
}

.dropdown-item:hover {
  background: #fdf1d5;
}

.dropdown-item.muted {
  color: #8a7a52;
}

.dropdown-item.danger {
  color: #c0392b;
}
</style>
