<template>
  <div id="app">
    <nav-bar />
  </div>
</template>

<script>
import NavBar from './components/NavBar'
export default {
  name: 'app',
  components: {
    NavBar
  }
}
</script>

<style>
  * {
    font-family: 'Philosopher', sans-serif;
    background-color: #FFD58E;

    box-sizing: border-box;
    margin: 0;
    padding: 0;

    text-align: center;
  }
  .nav {
    background-color: #EC2F3B;
  }
  a {
    color: white;
    text-decoration: none;
  }

</style>