import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import ToolSearch from './views/ToolSearch.vue'
import Tools from './views/Tools.vue'
import ToolDetail from './views/ToolDetail.vue'
import Reserve from './views/Reserve.vue'
import Cart from './views/Cart.vue'

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
      path: "/toolsearch",
      name: "tool-search",
      component: ToolSearch,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/tools',
      name: 'tools',
      component: Tools,
      meta: {
        // correct setting
        // requiresAuth: true

        // temporary for development
        requiresAuth: false
      }
    },
    {
      path: '/tools/:id',
      name: 'tool-detail',
      component: ToolDetail,
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
