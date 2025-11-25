import { defineStore } from 'pinia'
import type { AdminProfile } from '@/services/auth'
// Pinia 状态，保存 token 和 AdminProfile（localStorage 永久化）并提供 setSession/clear。
//是 Pinia 仓库，负责保存 token 与用户资料，并在 setSession/clear 时同步到 localStorage。第一次加载页面就能从本地缓存恢复登录状态，isAuthenticated getter 供路由守卫或页面判断。
const TOKEN_KEY = 'cqwm_admin_token'
const PROFILE_KEY = 'cqwm_admin_profile'

interface AuthState {
  token: string | null
  profile: AdminProfile | null
}

function loadProfile(): AdminProfile | null {
  const raw = localStorage.getItem(PROFILE_KEY)
  return raw ? (JSON.parse(raw) as AdminProfile) : null
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem(TOKEN_KEY),
    profile: loadProfile(),
  }),
  getters: {
    isAuthenticated: (state) => Boolean(state.token),
  },
  actions: {
    setSession(token: string, profile: AdminProfile) {
      this.token = token
      this.profile = profile
      localStorage.setItem(TOKEN_KEY, token)
      localStorage.setItem(PROFILE_KEY, JSON.stringify(profile))
    },
    clear() {
      this.token = null
      this.profile = null
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(PROFILE_KEY)
    },
  },
})
