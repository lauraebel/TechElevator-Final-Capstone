<template>
  <div class="home">
    <h1>Welcome to the Tool Lending Library!</h1>
    <img src="../assets/images/homepage/diy.jpg" alt="diy" />
    <h2>With over 60 tools to choose from, we are a one-stop shop for your next project!</h2>
    <h3>Choose from the dropdown menu to search and select tools, see your current loans, or check your upcoming tool reservations.</h3>
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