import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/submit'
  },
  {
    path: '/submit',
    name: 'QuestionSubmit',
    component: () => import('../views/QuestionSubmit.vue'),
    meta: { title: '提交问题' }
  },
  {
    path: '/admin',
    name: 'AdminLogin',
    component: () => import('../views/AdminLogin.vue'),
    meta: { title: '管理员登录' }
  },
  {
    path: '/admin/questions',
    name: 'QuestionManagement',
    component: () => import('../views/QuestionManagement.vue'),
    meta: { title: '问题管理', requiresAuth: true }
  },
  {
    path: '/admin/answer/:id',
    name: 'QuestionAnswer',
    component: () => import('../views/QuestionAnswer.vue'),
    meta: { title: '回答问题', requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue'),
    meta: { title: '页面未找到' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由前置守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 问题收集系统` : '问题收集系统'
  
  // 简单的鉴权逻辑（实际项目中应该使用更完善的鉴权方案）
  if (to.meta.requiresAuth) {
    const isAdmin = localStorage.getItem('isAdmin')
    if (isAdmin === 'true') {
      next()
    } else {
      next('/admin')
    }
  } else {
    next()
  }
})

export default router 