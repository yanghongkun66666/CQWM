<script setup lang="ts">
import { onMounted, reactive, ref, computed } from 'vue'
import AdminShell from '@/layouts/AdminShell.vue'
import {
  fetchEmployees,
  createEmployee,
  updateEmployee,
  updateEmployeeStatus,
  type Employee,
} from '@/services/employees'

const filters = reactive({
  name: '',
  page: 1,
  pageSize: 10,
})
const targetPage = ref(1)

const list = ref<Employee[]>([])
const total = ref(0)
const loading = ref(false)
const showForm = ref(false)
const editing = ref<Employee | null>(null)
const errorModal = reactive({ open: false, title: '请求错误', detail: '' })
const message = ref('')
const form = reactive({
  name: '',
  username: '',
  phone: '',
  gender: 'MALE' as 'MALE' | 'FEMALE',
  idNumber: '',
})

const totalPages = computed(() => Math.max(1, Math.ceil(total.value / filters.pageSize)))

const loadData = async () => {
  loading.value = true
  message.value = ''
  try {
    const data = await fetchEmployees({ name: filters.name, page: filters.page, pageSize: filters.pageSize })
    list.value = data.records
    total.value = data.total
  } catch (e) {
    handleError(e, '加载员工失败')
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  editing.value = null
  form.name = ''
  form.username = ''
  form.phone = ''
  form.gender = 'MALE'
  form.idNumber = ''
  showForm.value = true
}

const openEdit = (row: Employee) => {
  editing.value = row
  form.name = row.name
  form.username = row.username
  form.phone = row.phone || ''
  form.gender = row.gender || 'MALE'
  form.idNumber = row.idNumber || ''
  showForm.value = true
}

const submitForm = async () => {
  message.value = ''
  try {
    if (editing.value) {
      await updateEmployee(editing.value.id, { ...form })
    } else {
      await createEmployee({ ...form })
    }
    showForm.value = false
    await loadData()
    message.value = '保存成功'
  } catch (e) {
    handleError(e, '保存失败')
  }
}

const toggleStatus = async (row: Employee) => {
  const nextStatus = row.status === 'ENABLED' ? 'DISABLED' : 'ENABLED'
  message.value = ''
  try {
    await updateEmployeeStatus(row.id, nextStatus)
    await loadData()
    message.value = '状态更新成功'
  } catch (e) {
    handleError(e, '状态更新失败')
  }
}

const gotoPage = (page: number) => {
  filters.page = Math.min(Math.max(1, page), totalPages.value)
  targetPage.value = filters.page
  loadData()
}

const handleError = (error: unknown, fallback: string) => {
  let detail = fallback
  if (error && typeof error === 'object' && 'response' in error) {
    const resp: any = (error as any).response
    detail = resp?.data?.message || resp?.data?.error || resp?.statusText || fallback
  } else if (error instanceof Error) {
    detail = error.message
  }
  message.value = ''
  errorModal.detail = detail
  errorModal.open = true
}

onMounted(loadData)
</script>

<template>
  <AdminShell>
    <div class="page">
      <header class="page-head">
        <div class="search">
          <label>员工姓名：</label>
          <input v-model="filters.name" type="text" placeholder="请输入员工姓名" />
          <button class="primary" @click="gotoPage(1)">查询</button>
        </div>
        <button class="add" @click="openAdd">+ 添加员工</button>
      </header>

      <section class="card" v-if="!loading">
        <div v-if="message" class="alert">{{ message }}</div>
        <table class="table">
          <thead>
            <tr>
              <th>员工姓名</th>
              <th>账号</th>
              <th>手机号</th>
              <th>账号状态</th>
              <th>最后操作时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="row in list" :key="row.id">
              <td>{{ row.name }}</td>
              <td>{{ row.username }}</td>
              <td>{{ row.phone || '—' }}</td>
              <td>
                <span class="dot" :class="{ enabled: row.status === 'ENABLED' }"></span>
                {{ row.status === 'ENABLED' ? '启用' : '禁用' }}
              </td>
              <td>{{ row.lastOperateAt?.replace('T', ' ') || '—' }}</td>
              <td>
                <button class="link" @click="openEdit(row)">修改</button>
                <button class="link danger" @click="toggleStatus(row)">
                  {{ row.status === 'ENABLED' ? '禁用' : '启用' }}
                </button>
              </td>
            </tr>
            <tr v-if="!list.length">
              <td colspan="6" class="empty">暂无数据</td>
            </tr>
          </tbody>
        </table>

        <footer class="pager">
          <div>共 {{ total }} 条</div>
          <div class="pager-controls">
            <button :disabled="filters.page <= 1" @click="gotoPage(filters.page - 1)">上一页</button>
            <span>{{ filters.page }} / {{ totalPages }}</span>
            <button :disabled="filters.page >= totalPages" @click="gotoPage(filters.page + 1)">下一页</button>
            <span>跳至</span>
            <input class="pager-input" v-model.number="targetPage" type="number" min="1" :max="totalPages" />
            <span>页</span>
            <button @click="gotoPage(targetPage || 1)">确定</button>
          </div>
        </footer>
      </section>

      <div v-else class="loading">加载中...</div>

      <div v-if="errorModal.open" class="modal-backdrop" @click.self="errorModal.open = false">
        <div class="modal">
          <h3>{{ errorModal.title }}</h3>
          <p>{{ errorModal.detail }}</p>
          <div class="dialog-actions">
            <button class="primary" @click="errorModal.open = false">我知道了</button>
          </div>
        </div>
      </div>

      <div v-if="showForm" class="dialog-backdrop" @click.self="showForm = false">
        <div class="dialog">
          <h3 class="dialog-title">{{ editing ? '修改员工' : '新增员工' }}</h3>
          <label>
            <span>账号</span>
            <input v-model="form.username" type="text" placeholder="请输入账号" />
          </label>
          <label>
            <span>员工姓名</span>
            <input v-model="form.name" type="text" placeholder="请输入员工姓名" />
          </label>
          <label>
            <span>手机号</span>
            <input v-model="form.phone" type="text" placeholder="手机号" />
          </label>
          <label class="gender">
            <span>性别</span>
            <div class="gender-options">
              <label>
                <input v-model="form.gender" type="radio" value="MALE" />
                男
              </label>
              <label>
                <input v-model="form.gender" type="radio" value="FEMALE" />
                女
              </label>
            </div>
          </label>
          <label>
            <span>身份证号</span>
            <input v-model="form.idNumber" type="text" />
          </label>
          <div class="dialog-actions">
            <button class="secondary" @click="showForm = false">取消</button>
            <button class="primary" @click="submitForm">保存</button>
          </div>
        </div>
      </div>
    </div>
  </AdminShell>
</template>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.page-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search input {
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.primary {
  background: #ffb84d;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  color: #fff;
  cursor: pointer;
}

.add {
  background: #ffb84d;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  color: #fff;
  cursor: pointer;
}

.card {
  background: #fff;
  border-radius: 12px;
  padding: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.04);
}

.alert {
  margin-bottom: 10px;
  padding: 8px 10px;
  border-radius: 8px;
  background: #fff4e5;
  color: #a55d00;
  border: 1px solid #ffd9a8;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  color: #111;
}

.table th {
  color: #555;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  display: inline-block;
  margin-right: 6px;
  background: #d8d8d8;
}

.dot.enabled {
  background: #25c16f;
}

.link {
  border: none;
  background: transparent;
  color: #3b7cff;
  cursor: pointer;
  margin-right: 8px;
}

.link.danger {
  color: #d9534f;
}

.empty {
  text-align: center;
  color: #999;
}

.pager {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 0 0;
  color: #111;
}

.pager-controls {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #111;
}

.pager-input {
  width: 60px;
  padding: 6px 8px;
  border: 1px solid #ddd;
  border-radius: 6px;
  color: #111;
}

.pager-controls button {
  padding: 6px 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background: #fff;
  cursor: pointer;
  color: #111;
}

.pager-controls button:disabled {
  color: #ccc;
  cursor: not-allowed;
}

.loading {
  padding: 20px;
}

.dialog-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.35);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 20;
}

.dialog {
  background: #fff;
  padding: 18px;
  border-radius: 12px;
  min-width: 320px;
  display: grid;
  gap: 12px;
}

.dialog-title {
  margin: 0;
  font-weight: 700;
  color: #333;
}

.dialog label {
  display: grid;
  gap: 6px;
  color: #111;
}

.dialog .gender {
  align-items: center;
}

.gender-options {
  display: flex;
  gap: 14px;
}

.dialog input {
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
  color: #111;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.secondary {
  border: 1px solid #ccc;
  background: #fff;
  border-radius: 6px;
  padding: 8px 12px;
  cursor: pointer;
}

.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 30;
}

.modal {
  background: #fff;
  padding: 18px;
  border-radius: 12px;
  min-width: 280px;
  max-width: 420px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
}

.modal h3 {
  margin: 0 0 8px;
  color: #c0392b;
}

.modal p {
  margin: 0 0 12px;
  color: #333;
  word-break: break-word;
}
</style>
