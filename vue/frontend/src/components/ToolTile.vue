<template>
  <div class="tool" v-if="this.tool.toolId != null">
      
      <router-link :to="{name:'tool', params: {id: this.tool.toolId}}" >
        <h1 class="tool-name">{{tool.name}}</h1>
        <div class="tool-img">
            <img v-bind:src="('@/assets/images/product-img/' + this.tool.toolImgName)" :alt="this.tool.toolName" />
        </div>
      </router-link>
      <add-to-cart v-on:clickedCart="addToCart" />
  </div>
</template>

<script>
import AddToCart from './AddToCart'

export default {
    name: 'tool-tile',
    props: {
        tool: Object,
    },
    components: {
        AddToCart
    },
    data() {
        return {
            addedToCart: false,
            isAvailable: true,
        }
    },
    methods: {
        addToCart() {
            this.addedToCart = !this.addedToCart;
        }
    }
}
</script>

<style>
    
</style>