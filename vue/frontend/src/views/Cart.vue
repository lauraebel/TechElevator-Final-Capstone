<template>
  <div class="cart-contents">
    <h1 class="page-title">Cart</h1>
    <tool-tile class="cart-item" v-for="item in tools" v-bind:key="item" v-bind:tool="item" />
  </div>
</template>

<script>
import ToolTile from "../components/ToolTile";
import auth from '../auth';

export default {
  name: "cart",
  components: {
    ToolTile
  },
  props: {
    cart: Object
  }, 
  data() {
    return {
      apiURL: "http://localhost:8080/AuthenticationApplication/api",
      tools: [],
      user: {}
    }
  },
  methods: {
    getTools(){
      for (let i = 0; i < this.cart.items.length; i++){
        let item = this.cart.items[i];

        fetch(this.apiURL + "/tools/" + item)
        .then(response => {
          return response.json();
        })
        .then (data => {
          this.tools.push(data);
        })
        .catch(err => {
          console.error(err);
        });
      }
    },
    getUser(){
      this.user = auth.getUser();
    },
    getCart(){
      fetch(process.env.VUE_APP_TOOLS_API + "/cart/" + this.user.iat)
        .then(response => {
          return response.json();
        })
        .then (data => {
          this.cart = data; 
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.getTools();
    this.getUser();
  }
}
</script>

<style>

</style>