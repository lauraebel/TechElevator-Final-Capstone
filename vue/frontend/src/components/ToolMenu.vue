<template>
  <div id="menu">
      <h1>Tools</h1>
      <div class="tool dropdown">
          <router-link :to="{name: 'tool-search'}">Find a Tool</router-link>
          <router-link :to="{name: 'tools'}">All Tools</router-link>
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
    h1 {
        background-color: #EC2F3B;
    }
    h1:hover {
        background-color: #F26249;
    }
    a {
        width: 100%;
    }
</style>