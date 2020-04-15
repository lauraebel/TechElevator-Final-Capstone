<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-bind:isVisible="visible" v-bind:role="userRole" />
    <router-view class="content" v-on:userRole="handleLoggedIn" />
    <router-link :to="{ name: 'credits' }" id="footer">Credits</router-link>
  </div>
</template>

<script>
import auth from './auth'
import MenuIcon from './components/MenuIcon'
import DropdownMenu from './components/DropdownMenu'
import Branding from './components/Branding'
import CartIcon from './components/CartIcon'

export default {
  name: 'app',
  components: {
    Branding,
    CartIcon,
    DropdownMenu,
    MenuIcon
    },
  data (){
    return {
      visible: false,
      userRole: 0
    }
  },
  methods: {
    handleMenu() {
      this.visible = !this.visible;
    },
    handleLoggedIn(event){
      this.userRole = event;
    },
    getRole() {
      if (auth.getUser() !== null){
      this.userRole = auth.getUser().rol;
      }
    }
  },
  created() {
    this.getRole();
  }
}
</script>

<style>
@import "./assets/style/style.css";
</style>
