<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-on:clickedLink="handleMenu" v-bind:isVisible="visible" />
    <router-view class="content" />
  </div>
</template>

<script>
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
    MenuIcon,
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
    }
  },
  created() {
  }
}
</script>

<style>
@import "./assets/style/style.css";
</style>
