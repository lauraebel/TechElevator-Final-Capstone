<template>
  <router-link
    :to="{ name: 'tool', params: { id: this.tool.toolId } }"
    class="tool-info"
  >
    <h1 class="tool-name">{{ tool.toolName }}</h1>
    <div class="img-container">
      <img
        v-bind:src="require('../assets/images/product-img/' + tool.toolImgName)"
        :alt="'image of ' + this.tool.toolName"
        class="tool-img"
      />
    </div>
  </router-link>
</template>

<script>
export default {
  name: "tool-tile",
  props: {
    tool: Object
  },
  components: {},
  data() {
    return {
      addedToCart: false,
      isAvailable: true
    };
  },
  methods: {
    clickedCart() {
      this.addedToCart = !this.addedToCart;
      this.$emit("clickedCart", this.tool.toolId);
    }
  }
};
</script>

<style></style>
