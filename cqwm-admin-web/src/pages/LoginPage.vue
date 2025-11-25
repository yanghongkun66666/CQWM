<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/auth'
import { login, type LoginPayload } from '@/services/auth'

const authStore = useAuthStore()
const router = useRouter()
const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const form = reactive<LoginPayload>({
  username: '',
  password: '',
  rememberMe: true,
})

const handleSubmit = async () => {
  if (loading.value) return
  errorMessage.value = ''
  successMessage.value = ''
  loading.value = true
  try {
    const session = await login(form)
    authStore.setSession(session.token, session.profile)
    successMessage.value = `欢迎回来，${session.profile.fullName}`
    router.push({ name: 'dashboard' })
  } catch (error) {
    if (error instanceof Error) {
      errorMessage.value = error.message
    } else {
      errorMessage.value = '登录失败，请稍后重试'
    }
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-screen">
    <section class="hero">
      <h1>CQWM 商家管理端</h1>
      <p>在一个后台完成门店、菜品、订单与配送的全链路管理。</p>
    </section>

    <section class="panel">
      <h2>账号登录</h2>
      <form @submit.prevent="handleSubmit">
        <label>
          <span>用户名</span>
          <input v-model="form.username" type="text" placeholder="请输入登录账号" required autocomplete="username" />
        </label>
        <label>
          <span>密码</span>
          <input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            required
            autocomplete="current-password"
          />
        </label>
        <div class="form-meta">
          <label class="checkbox">
            <input v-model="form.rememberMe" type="checkbox" />
            <span>7天内免登录</span>
          </label>
          <a class="link" href="#" @click.prevent>忘记密码？</a>
        </div>
        <button type="submit" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
        <p v-if="errorMessage" class="feedback error">{{ errorMessage }}</p>
        <p v-if="successMessage" class="feedback success">{{ successMessage }}</p>
      </form>
      <small class="tip">测试账号：admin / Admin@123（可在数据库中修改）</small>
    </section>
  </div>
</template>

<style scoped>
.login-screen {
  min-height: 100vh;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  align-items: center;
  padding: 4rem clamp(1rem, 5vw, 6rem);
  gap: 2rem;
  color: #fff;
  background: radial-gradient(circle at top, #243b55, #141e30 65%);
}

.hero h1 {
  font-size: clamp(2.4rem, 5vw, 3.5rem);
  margin-bottom: 1rem;
}

.hero p {
  font-size: 1.1rem;
  color: #dfe7ff;
  max-width: 480px;
}

.panel {
  background: rgba(12, 18, 32, 0.85);
  border-radius: 24px;
  padding: 2.5rem;
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(10px);
}

.panel h2 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

label span {
  display: block;
  margin-bottom: 0.35rem;
  font-size: 0.9rem;
  color: #b4c0d9;
}

input[type='text'],
input[type='password'] {
  width: 100%;
  padding: 0.85rem 1rem;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  background: rgba(255, 255, 255, 0.05);
  color: #fff;
  font-size: 1rem;
}

input:focus {
  outline: none;
  border-color: #4f9cff;
  background: rgba(79, 156, 255, 0.08);
}

button {
  border: none;
  border-radius: 999px;
  padding: 0.9rem;
  font-size: 1rem;
  font-weight: 600;
  background: linear-gradient(135deg, #4f9cff, #646cff);
  color: #fff;
  cursor: pointer;
  transition: opacity 0.2s ease;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.form-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #b4c0d9;
}

.checkbox {
  display: inline-flex;
  gap: 0.35rem;
  align-items: center;
}

.link {
  color: #82b1ff;
  text-decoration: none;
}

.feedback {
  font-size: 0.9rem;
  margin: 0;
}

.feedback.error {
  color: #ff8a80;
}

.feedback.success {
  color: #a5ffb4;
}

.tip {
  display: block;
  margin-top: 1rem;
  color: #b4c0d9;
}

@media (max-width: 768px) {
  .login-screen {
    padding: 2rem 1.5rem;
  }

  .panel {
    padding: 1.5rem;
  }
}
</style>
