<template>
  <div class="cart-contents">
    <h1 class="page-title">Cart</h1>
    <tool-tile class="cart-item" v-for="item in cart.items" v-bind:key="item.toolId" v-bind:tool="item" />
    <checkout />
  </div>
</template>

<script>
import ToolTile from "../components/ToolTile";
import Checkout from "../components/Checkout";
import auth from '../auth';

export default {
  name: "cart",
  components: {
    ToolTile,
    Checkout
  },
  props: {
    user: Object,
  }, 
  data() {
    return {
       cart: {}
    }
  },
  methods: {
    getCart(){
      fetch( `${process.env.VUE_APP_REMOTE_API}/api/cart/${auth.getUser().sub}`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cart = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
  },
  created() {
    this.getCart();
  }
}
</script>

<style>

</style>