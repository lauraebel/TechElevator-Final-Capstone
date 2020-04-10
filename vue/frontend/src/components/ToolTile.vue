<template>
  <div class="tile" v-if="this.tool.toolId != null">
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
    <div class="add">
        <span>Add to Cart</span>
        <add-to-cart v-on:clickedCart="addToCart" />
    </div>
  </div>
</template>

<script>
import AddToCart from "./AddToCart";

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
            fetch(this.apiURL + "/cart/{userId}")
                .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.userCart = data;
                })
                .catch( err => { console.error(err) });
        }
    }
  
};
</script>

<style></style>
