import { httpClient, type ApiResponse } from './http'
// 封装 login(payload)，调用 /api/v1/admin/auth/login 并返回 LoginResult。
//通过 httpClient.post('/api/v1/admin/auth/login', payload) 调后端登录接口，收到的 ApiResponse<LoginResult> 会在 success=false 时直接抛错给上层。

export interface LoginPayload {
  username: string
  password: string
  rememberMe: boolean
}

export interface AdminProfile {
  id: number
  username: string
  fullName: string
  phone?: string
  email?: string
}

export interface LoginResult {
  token: string
  expiresIn: number
  profile: AdminProfile
}

export async function login(payload: LoginPayload) {
  const { data } = await httpClient.post<ApiResponse<LoginResult>>(
    '/api/v1/auth/login',
    payload,
  )
  if (!data.success) {
    throw new Error(data.message || '登录失败')
  }
  return data.data
}

export async function logout() {
  // Stateless; backend simply returns success. Client clears session afterward.
  await httpClient.post<ApiResponse<void>>('/api/v1/auth/logout')
}
