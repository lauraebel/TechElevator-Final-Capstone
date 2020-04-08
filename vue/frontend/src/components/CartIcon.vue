<template>
  <div class="cart">
      <router-link :to="{name: 'cart'}">
        <img src="@/assets/images/icons/mobile-cart.png" class="mobile-cart-icon" />
        <img src="@/assets/images/icons/cart.png" class="cart-icon" />
      </router-link>
    </div>
</template>

<script>
export default {
	name: 'cart-icon'
}
</script>

<style>

</style>