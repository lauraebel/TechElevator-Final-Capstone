<template>
  <div class="dropdown" >
    <transition name="fade">
      <div v-if="isVisible" v-on:click="clickedLink">
        <router-link v-on:click="collapseMenu" class="clickable" :to="{name: 'tools'}">Find a Tool</router-link>
        <router-link class="clickable" :to="{name: 'user-loans'}">My Loans</router-link> 
        <router-link class="clickable" :to="{name: 'reservations'}">My Reservations</router-link>
        <router-link class="clickable" :to="{name: 'about'}">About</router-link> 
        <div class="sign-out clickable" v-on:click="signOut()">Sign Out</div>
      </div>
    </transition>
    <!-- admin menu, uncomment when login is implemented -->
    <!-- add to display admin menu if user is admin: v-else-if="role === 1" -->
    <!-- <transition name="fade">  
      <div v-if="isVisible">
        <router-link :to="{name: 'tools'}">Available Tools</router-link>
        <router-link :to="{name: 'tool-search'}">Find a Loan</router-link>
        <router-link :to="{name: 'tools'}">Sign Out</router-link>
        change to button to sign out ^
      </div>
    </transition> -->
  </div>
</template>

<script>
import auth from '../auth';

export default {
  name: "dropdown-menu",
  props: {
    //role: Number,
    isVisible: Boolean
  },
  data(){
    return {
      show: false
    }
  },
  methods: {
    isUser(){
      return this.role > 0;
    },
    isAdmin() {
      return this.role == 1;
    },
    signOut(){
      auth.logout();
      this.$router.go('/login');
    },
    clickedLink(){
      this.$emit('clickedLink', false);
    }
  }
};
</script>

<style>
</style>