<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-bind:isVisible="visible"/>
    <add-to-cart v-on:clickedCart="addToCart"/>
    <router-view class="content"/>
    <router-link :to="{name: 'credits'}" id="footer" >Credits</router-link>
  </div>
</template>

<script>
import MenuIcon from './components/MenuIcon'
import DropdownMenu from './components/DropdownMenu'
import Branding from './components/Branding'
import CartIcon from './components/CartIcon'
import AddToCart from './components/AddToCart'

export default {
  name: 'app',
  components: {
    Branding,
    CartIcon,
    DropdownMenu,
    MenuIcon,
    AddToCart
  },
  data (){
    return {
      visible: false,
      addedToCart: false
    }
  },
  methods: {
    handleMenu() {
      this.visible = !this.visible;
    },
    AddToCart() {
      this.addedToCart = !this.addedToCart;
    }
  }
}
</script>

<style>
  @import './assets/style/style.css';
</style>