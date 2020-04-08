<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
  </div>
</template>

<script>
export default {
  name: 'home',
  data() {
    return {
      apiUrl: process.env.VUE_APP_API_URL
    }
  }
}
</script>

<style>

</style>