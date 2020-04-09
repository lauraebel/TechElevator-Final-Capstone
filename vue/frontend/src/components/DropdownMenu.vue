<template>
  <div class="dropdown">
    <!-- add to display user menu if is gen user: v-if="role === 0" -->
    <transition name="fade">
      <div v-if="isVisible">
        <router-link :to="{name: 'tools'}">Find a Tool</router-link>
        <router-link :to="{name: 'user-loans'}">My Loans</router-link>
        <router-link :to="{name: 'tools'}">Sign Out</router-link>
        <!-- change to button to sign out ^ -->
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
export default {
  name: "dropdown-menu",
  props: {
    role: Number,
    isVisible: Boolean
  }
};
</script>

<style>
</style>