<template>
  <div class="add-to-cart" >
    <transition name="fade">
        <button v-if="isAvailable && !addedToCart" v-on:click="clickedCart"><img src="@/assets/images/icons/add-to-cart.png" class="add-to-cart-icon" /><img src="@/assets/images/icons/desktop-add-to-cart.png" class="desktop-add-to-cart-icon" /></button>
        <button v-if="!isAvailable" :disabled='isDisabled'><img src="@/assets/images/icons/add-to-cart.png" class="can-not-add-to-cart-icon" /></button>
        <button v-if="addedToCart" v-on:click="clickedCart"><img src="@/assets/images/icons/in-cart.png" class="mobile-in-cart-icon" /><img src="@/assets/images/icons/desktop-in-cart.png" class="desktop-in-cart-icon" /></button>
    </transition> 
  </div>
</template>

<script>
import auth from '../auth';

export default {
  name: "add-to-cart",
  props: {
    toolId,
    userId
  },
  data (){
    return {
        addedToCart: false,
        isAvailable: true
    }
  }, 
  methods: {
    clickedCart() {
      this.addedToCart = !this.addedToCart;
      this.$emit('clickedCart', this.addedToCart);
    },
    addToCart(toolId) {
      var tempCart = JSON.parse(JSON.stringify(this.userCart));
      tempCart.items.push(toolId);
      fetch(this.apiURL + "/cart/" + this.user.getId , {
        method: 'PUT',
        body: JSON.stringify(tempCart)
        })
        .then( (response) => {
          return response.json();
        })
        .then( data => {
           this.userCart = data;
        })
        .catch( err => { 
          console.error(err) 
        });
    }
  }
};
</script>