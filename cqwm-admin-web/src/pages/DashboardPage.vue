<script setup lang="ts">
import AdminShell from '@/layouts/AdminShell.vue'

const today = '2022.09.21'
const tiles = [
  { label: '营业额', value: '¥', highlight: false },
  { label: '有效订单', value: '—', highlight: false },
  { label: '订单完成率', value: 'NaN%', highlight: true },
  { label: '平均客单价', value: '¥', highlight: false },
  { label: '新增用户', value: '—', highlight: false },
]

const orderStats = [
  { label: '待接单', value: '—', icon: '📄' },
  { label: '待派送', value: '🚚', icon: '🚚' },
  { label: '已完成', value: '🧾', icon: '🧾' },
  { label: '已取消', value: '❌', icon: '❌' },
  { label: '全部订单', value: '🗂', icon: '🗂' },
]

const goodsSummary = [
  { label: '已启售', value: '—' },
  { label: '已停售', value: '—' },
]
</script>

<template>
  <AdminShell>
    <section class="card-group">
      <header class="section-head">
        <div>今日数据</div>
        <span class="muted">{{ today }}</span>
        <a class="link" href="#">详细数据</a>
      </header>
      <div class="tiles">
        <div v-for="tile in tiles" :key="tile.label" class="tile">
          <div class="tile-label">{{ tile.label }}</div>
          <div class="tile-value" :class="{ highlight: tile.highlight }">{{ tile.value }}</div>
        </div>
      </div>
    </section>

    <section class="card-group">
      <header class="section-head">
        <div>订单管理</div>
        <span class="muted">{{ today }}</span>
        <a class="link" href="#">订单明细</a>
      </header>
      <div class="tiles">
        <div v-for="stat in orderStats" :key="stat.label" class="tile">
          <div class="tile-icon">{{ stat.icon }}</div>
          <div class="tile-label">{{ stat.label }}</div>
          <div class="tile-value">—</div>
        </div>
      </div>
    </section>

    <section class="card-split">
      <div class="card">
        <header class="section-head">
          <div>菜品总览</div>
          <a class="link" href="#">菜品管理</a>
        </header>
        <div class="tiles">
          <div v-for="item in goodsSummary" :key="item.label" class="tile">
            <div class="tile-icon">🏷</div>
            <div class="tile-label">{{ item.label }}</div>
            <div class="tile-value">—</div>
          </div>
          <button class="action">新增菜品</button>
        </div>
      </div>
      <div class="card">
        <header class="section-head">
          <div>套餐总览</div>
          <a class="link" href="#">套餐管理</a>
        </header>
        <div class="tiles">
          <div v-for="item in goodsSummary" :key="item.label" class="tile">
            <div class="tile-icon">🏷</div>
            <div class="tile-label">{{ item.label }}</div>
            <div class="tile-value">—</div>
          </div>
          <button class="action">新增套餐</button>
        </div>
      </div>
    </section>

    <section class="card">
      <header class="section-head">
        <div>订单信息</div>
      </header>
      <div class="tabbar">
        <button class="tab active">待接单</button>
        <button class="tab">待派送</button>
      </div>
    </section>
  </AdminShell>
</template>

<style scoped>
.card-group,
.card {
  background: #fff;
  border-radius: 14px;
  padding: 18px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.03);
}

.card-group + .card-group {
  margin-top: 8px;
}

.section-head {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 700;
  margin-bottom: 12px;
}

.section-head .muted {
  color: #a0a6b3;
  font-size: 13px;
  margin-left: auto;
}

.section-head .link {
  color: #a0a6b3;
  font-size: 13px;
  text-decoration: none;
}

.tiles {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 12px;
}

.tile {
  background: #fff7e6;
  border: 1px solid #ffe9c2;
  border-radius: 12px;
  padding: 14px;
  display: grid;
  gap: 6px;
}

.tile-label {
  color: #8b908f;
  font-size: 14px;
}

.tile-value {
  font-size: 22px;
  font-weight: 800;
  color: #2b3240;
}

.tile-value.highlight {
  color: #f38b00;
}

.tile-icon {
  font-size: 18px;
}

.card-split {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 12px;
}

.action {
  background: linear-gradient(135deg, #ffb84d, #ff9400);
  border: none;
  border-radius: 12px;
  color: #fff;
  padding: 14px;
  font-weight: 700;
  cursor: pointer;
  box-shadow: 0 10px 20px rgba(255, 148, 0, 0.18);
}

.tabbar {
  display: inline-flex;
  gap: 8px;
  margin-top: 12px;
}

.tab {
  border: none;
  padding: 10px 18px;
  border-radius: 10px;
  background: #f0f2f5;
  color: #555;
  cursor: pointer;
}

.tab.active {
  background: #ffb84d;
  color: #fff;
}
</style>
