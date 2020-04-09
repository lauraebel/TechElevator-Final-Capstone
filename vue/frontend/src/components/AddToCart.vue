<template>
  <div class="add-to-cart">
    <transition name="fade">
      <div v-if="isAvailable">
        <button v-on:click="clickedCart"><img src="@/assets/images/icons/add-to-cart.png" class="add-to-cart-icon" /></button>
      </div>
      <div v-if="isNotAvailable">
        <button :disabled='isDisabled'><img src="@/assets/images/icons/add-to-cart.png" class="can-not-add-to-cart-icon" /></button> 
      </div>
      <div v-if="addedToCart">
        <button v-on:click="clickedCart"><img src="@/assets/images/icons/in-cart.png" class="in-cart-icon" /></button>
      </div>
    </transition> 
  </div>
</template>

<script>
export default {
  name: "add-to-cart",
  props: {
    isAvailable: Boolean
  },
  data (){
    return {
        addedToCart: false
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