<template>
  <div class="cart">
    <h1 class="page-title">Tool Library Cart</h1>
    <div class="cartContents" v-for="item in this.userCart" :key="cart.tool_id"> 
      <router-link
        :to="{ name: 'tool', params: { id: this.tool.toolId } }"
        class="tool-info">
        <h1 class="tool-name">{{ tool.toolName }}</h1>
        <div class="img-container">
          <img
            v-bind:src="
              require('../assets/images/product-img/' + tool.toolImgName)
            "
            :alt="'image of ' + this.tool.toolName"
            class="tool-img"
          />
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import auth from '../auth';

export default {
  name: 'userCart',
  data() {
    return {
      apiURL: "http://localhost:8080/AuthenticationApplication/api",
      // apiURL: "https://5e8dd4e822d8cd0016a79b3f.mockapi.io",
      user: {},
      allCarts: [],
      userCart: [],
      allTools: []
    }
  },
  methods: {
    getCurrentUser(username) {
      username = this.user.sub;
      fetch(this.apiURL + "/users/" + username )
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.user = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    // method to get all carts
    getCarts(){
      fetch(this.apiURL + "/cart")
        .then( response => {
          return response.json();
        })
        .then( data => {
          this.allCarts = data;
        })
        .catch( err => { console.error(err) });
    },
    // method to get only current user cart
    getUserCart(){
      fetch(this.apiURL + "/cart/" + this.user.id)
        .then( response => {
          return response.json();
        })
        .then( data => {
          this.userCart = data;
        })
        .catch( err => { console.error(err) });
    },
    // method to get all tools
    getTools() {
      fetch(this.apiURL + "/tools")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.allTools = data;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  computed: {
      isAdmin(vm) {
        return this.user.rol === 'admin';
      }
  },
  created() {
    this.getCarts();
    this.getUserCart();
    this.getCurrentUser();
    this.user = auth.getUser();
  }
}
</script>

<style>

</style>