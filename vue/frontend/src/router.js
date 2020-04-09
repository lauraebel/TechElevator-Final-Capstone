import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'

import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'

import ToolSearch from './views/ToolSearch.vue'
import Tool from './views/Tool.vue'
import Reserve from './views/Reserve.vue'
import MyLoans from './views/MyLoans.vue'
import Cart from './views/Cart.vue'
import Credits from './views/Credits.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/tools',
      name: 'tools',
      component: ToolSearch,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/tools/:id',
      name: 'tool',
      component: Tool,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/loans',
      name: 'user-loans',
      component: MyLoans,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/reserve',
      name: 'reserve',
      component: Reserve,
      meta: {
        // correct setting
        // requiresAuth: true
        
        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/credits',
      name: 'credits',
      component: Credits,
      meta: {
        // correct setting
        // requiresAuth: true
        
        // temporary for development
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
