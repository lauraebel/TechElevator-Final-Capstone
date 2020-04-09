import Vue from 'vue'
import vSelect from 'vue-select'
import 'vue-select/dist/vue-select.css';
import ToggleButton from 'vue-js-toggle-button'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false
Vue.component('v-select', vSelect)
Vue.use(ToggleButton)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
