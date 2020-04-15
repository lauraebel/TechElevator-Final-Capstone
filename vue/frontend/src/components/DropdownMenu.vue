<template>
  <div class="dropdown" >
    <transition name="fade">
      <div v-if="isVisible">
        <router-link class="clickable" :to="{name: 'tools'}">Find a Tool</router-link>
        <router-link class="clickable" :to="{name: 'user-loans'}">My Loans</router-link> 
        <div class="sign-out clickable" v-on:click="signOut()">Sign Out</div>
      </div>
    </transition>

    <!-- conditionally show components based on role > works  -->
    <!-- <div v-if="isAdmin()">I am an admin</div>
    <div v-if="isUser()">I am a user</div> -->
    
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
    }
  }
};
</script>

<style>
</style>