<template>
  <nav class="nav">
      <router-link :to="{name: 'home'}" class="home nav">Home</router-link>
      <tool-menu class="toolMenu nav"/>
      <router-link :to="{name: 'cart'}" class="cart nav">Cart</router-link>
  </nav>
</template>

<script>
import ToolMenu from '../components/ToolMenu';

export default {
    name: 'nav-bar',
    components: {
        ToolMenu
    }
}
</script>

<style>
    nav {
        display: flex;
        justify-content: flex-start;
        align-content: center;

        background-color: #EC2F3B;
    }
    .cart {
        justify-self: flex-end;
        
    }
</style>