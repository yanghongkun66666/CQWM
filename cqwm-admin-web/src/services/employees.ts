import { httpClient, type ApiResponse } from './http'

export interface Employee {
  id: number
  name: string
  username: string
  phone?: string
  gender?: 'MALE' | 'FEMALE'
  idNumber?: string
  status: 'ENABLED' | 'DISABLED'
  lastOperateAt?: string
}

export interface EmployeeQueryParams {
  name?: string
  page?: number
  pageSize?: number
}

export interface PageResult<T> {
  records: T[]
  total: number
  page: number
  pageSize: number
}

export async function fetchEmployees(params: EmployeeQueryParams) {
  const { data } = await httpClient.get<ApiResponse<PageResult<Employee>>>('/api/v1/employees', {
    params,
  })
  if (!data.success) throw new Error(data.message)
  return data.data
}

export async function createEmployee(payload: {
  name: string
  username: string
  phone?: string
  gender: 'MALE' | 'FEMALE'
  idNumber?: string
}) {
  const { data } = await httpClient.post<ApiResponse<Employee>>('/api/v1/employees', payload)
  if (!data.success) throw new Error(data.message)
  return data.data
}

export async function updateEmployee(id: number, payload: {
  name: string
  username: string
  phone?: string
  gender: 'MALE' | 'FEMALE'
  idNumber?: string
}) {
  const { data } = await httpClient.put<ApiResponse<Employee>>(`/api/v1/employees/${id}`, payload)
  if (!data.success) throw new Error(data.message)
  return data.data
}

export async function updateEmployeeStatus(id: number, status: 'ENABLED' | 'DISABLED') {
  const { data } = await httpClient.patch<ApiResponse<void>>(`/api/v1/employees/${id}/status`, { status })
  if (!data.success) throw new Error(data.message)
}
