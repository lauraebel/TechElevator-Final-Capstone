<template>
  <div class="cart">
    <h1>Tool Library Cart</h1>
    <div class="cartContents" v-for="tool in this.userCart" :key="tool.toolId">{{tool.toolName}} </div>
  </div>
</template>

<script>
export default {
  name: 'cart',
  data() {
    return {
      apiURL: "http://localhost:8080/AuthenticationApplication/api/tools",
      allCarts: [],
      userCart: [],
      allBrands: [],
      allCategories: [],
      allTools: [],
      brand: '',
      category: '',
    }
  },
  methods: {
    getCarts(){
      fetch(this.apiURL + "/carts")
        .then( response => {
          return response.json();
        })
        .then( data => {
          this.allCarts = data;
        })
        .catch( err => { console.error(err) });
    },
    getUserCart(){
      fetch(this.apiURL + "/cart/{userId}")
        .then( response => {
          return response.json();
        })
        .then( data => {
          this.userCart = data;
        })
        .catch( err => { console.error(err) });
    }
  },
  computed: {
      
  }
}
</script>

<style>

</style>