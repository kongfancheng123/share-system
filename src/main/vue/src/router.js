import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

let router = new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      meta: {
        title: '登录'
      },
      component: () => import('./views/login.vue'),
      alias: '/login'
    },
    {
      path: '/game',
      component: () => import('./views/index.vue'),
      children: [
        {
          path: '/resourceInfo',
          name: 'resourceInfo',
          meta: {
            title: '资源'
          },
          component: () => import('./views/comprehensive/resourceInfo.vue')
        },
        {
            path: '/leaguerInfo',
            name: 'leaguerInfo',
            meta: {
            title: '会员'
            },
            component: () => import('./views/comprehensive/leaguerInfo.vue')
        },
        {
            path: '/resourceInfo1',
                name: 'resourceInfo1',
            meta: {
            title: '预约租赁'
        },
            component: () => import('./views/comprehensive/resourceInfo1.vue')
        }
      ]
    },

    {
      path: '*',
      name: 'Error',
      meta: {
        title: '404页面不存在'
      },
      component: () => import('./views/404.vue')
    }
  ]
})

// 全局路由卫士，设置文档title
router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  next()
})

export default router
