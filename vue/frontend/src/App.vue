<template>
  <div id="app">
    <branding />
    <cart-icon />
    <menu-icon v-on:clickedMenu="handleMenu" />
    <dropdown-menu v-bind:isVisible="visible" />
    <router-view class="content" />
    <credits id="footer" />
  </div>
</template>

<script>
import auth from './auth'
import MenuIcon from './components/MenuIcon'
import DropdownMenu from './components/DropdownMenu'
import Branding from './components/Branding'
import CartIcon from './components/CartIcon'
import Credits from './components/Credits'

export default {
  name: 'app',
  components: {
    Branding,
    CartIcon,
    DropdownMenu,
    MenuIcon,
    Credits
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
    testEmail(){
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/email`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
        },
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          return data;
        })
        .catch((err) => {
          console.error(err);
        });
    }
  },
  created() {
    //this.getRole();
    //this.testEmail();
  }
}
</script>

<style>
@import "./assets/style/style.css";
</style>
