import { createApp } from 'vue'
// 是入口，创建 Vue 应用后加载 Pinia 和 Router，再统一引入全局样式。所有业务页面都挂到这个 App。
import { createPinia } from 'pinia'
import router from './router'
import './style.css'
import App from './App.vue'
// 引入 Pinia + Router；
const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
