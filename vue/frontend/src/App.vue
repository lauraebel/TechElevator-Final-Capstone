<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-bind:isVisible="visible"/>
    <router-view class="content" v-on:clickedAddToCart="handleCart" v-bind:user="user" />
    <router-link :to="{name: 'credits'}" id="footer" >Credits</router-link>
  </div>
</template>

<script>
import auth from './auth';
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
      user: {},
      visible: false,
    }
  },
  methods: {
    handleMenu() {
      this.visible = !this.visible;
    },
    handleCart(event) {
      this.cart.items.push(event);
    }, 
    getUser() {
      this.user = auth.getUser();
    },
  },
  created() {
      this.getUser();
  }
}
</script>

<style>
  @import './assets/style/style.css';
</style>