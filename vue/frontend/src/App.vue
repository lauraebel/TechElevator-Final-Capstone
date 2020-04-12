<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-bind:isVisible="visible"/>
    <router-view class="content" v-on:clickedAddToCart="handleCart" v-bind:cart="cart" />
    <router-link :to="{name: 'credits'}" id="footer" >Credits</router-link>
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
    MenuIcon
    },
  data (){
    return {
      visible: false,
      cart: {
        user: '',
        items: [1,2,3,4,5]
      }
    }
  },
  methods: {
    handleMenu() {
      this.visible = !this.visible;
    },
    handleCart(event) {
      this.cart.items.push(event);
    }
  }
}
</script>

<style>
  @import './assets/style/style.css';
</style>