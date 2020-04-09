import Vue from 'vue'
import vSelect from 'vue-select'
import 'vue-select/dist/vue-select.css';
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false
//Vue.component('v-select', vSelect)
Vue.component('v-select', VueSelect.VueSelect);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
