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
    user: Object,
  }, 
  data() {
    return {
       cart: {}
    }
  },
  methods: {
    getCart(){
      fetch(`${process.env.VUE_APP_REMOTE_API}/cart/${this.user.sub}`, {
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
    }
  },
  created() {
    this.getCart();
  }
}
</script>

<style>

</style>