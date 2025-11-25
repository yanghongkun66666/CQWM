import axios from 'axios'
// Axios 实例，默认基地址 VITE_API_BASE_URL，请求拦截器自动带 Authorization: Bearer <token>。
//构建 Axios 客户端：基地址来自 .env.* 中的 VITE_API_BASE_URL，请求发出前会从 localStorage 取 cqwm_admin_token，自动塞到 Authorization: Bearer ...，这样后台以后加 JWT 校验时无需改调用代码。

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'

export const httpClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
})

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('cqwm_admin_token')
  if (token) {
    config.headers = config.headers ?? {}
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export interface ApiResponse<T> {
  success: boolean
  code: string
  message: string
  data: T
}
