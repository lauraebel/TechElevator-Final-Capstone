<template>
  <div class="add-to-cart">
    <transition name="fade">

        <button v-if="isAvailable && !addedToCart" v-on:click="clickedCart"><img src="@/assets/images/icons/add-to-cart.png" class="add-to-cart-icon" /></button>

        <button v-if="!isAvailable" :disabled='isDisabled'><img src="@/assets/images/icons/add-to-cart.png" class="can-not-add-to-cart-icon" /></button>

        <button v-if="addedToCart" v-on:click="clickedCart"><img src="@/assets/images/icons/in-cart.png" class="in-cart-icon" /></button>

    </transition> 
  </div>
</template>

<script>
export default {
  name: "add-to-cart",
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
    }
  }
};
</script>

<style>
#can-not-add-to-cart-icon {
    background: black;
}

#add-to-cart-icon {
    color: #EC2F3B; 
}
</style>