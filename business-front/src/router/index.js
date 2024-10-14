import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '/',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/auth/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/user',
      name: 'home',
      meta: { requiresAuth: true, userRole: 'user' },
      component: () => import('../views/UserHomeView.vue')
    },
    {
      path: '/forms',
      name: 'formView',
      meta: { requiresAuth: true, userRole: 'user' },
      component: () => import('../views/UserFormView.vue')
    },
    {
      path: '/admin',
      name: 'adminHome',
      meta: { requiresAuth: true, userRole: 'admin' },
      component: () => import('../views/AdminView.vue')
    },
    {
      path: '/employee',
      name: 'employeeHome',
      meta: { requiresAuth: true, userRole: 'employee' },
      component: () => import('../views/EmployeeView.vue')
    },
    {
      path: '/employee/forms',
      name: 'employeeForms',
      meta: { requiresAuth: true, userRole: 'employee' },
      component: () => import('../views/AdminFormsView.vue')
    },
    {
      path: '/admin/register',
      name: 'register',
      meta: { requiresAuth: true, userRole: 'admin' },
      component: () => import('../views/RegistrationView.vue')
    },
    {
      path: '/admin/modify',
      name: 'adminViewUsers',
      meta: { requiresAuth: true, userRole: 'admin' },
      component: () => import('../views/AdminViewUsers.vue')
    },    
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth
  const isAuthenticated = localStorage.getItem('token')
  const userRole = localStorage.getItem('userRole')
  if (requiresAuth && !isAuthenticated) {
    next('/auth/login')
  } else if ((isAuthenticated && to.path === '/auth/login') || to.path === '/') {
    if (userRole === 'admin') {
      next('/admin')
    } else if (userRole === 'user'){
      next('/user')
    } else {
      next('/employee')
    }
  } else {
    next()
  }
})

export default router
