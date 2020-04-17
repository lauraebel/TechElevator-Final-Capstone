<template>
  <div class="tool-detail">
    <h1>{{ tool.toolName }}</h1>
    <img :src="require('../assets/images/product-img/' + tool.toolImgName)" />
    <div class="info">
      <div class="description">{{ tool.toolDescription }}</div>
      <add-to-cart v-bind:tool="tool" />
    </div>
  </div>
</template>

<script>
import auth from "../auth";
import AddToCart from "../components/AddToCart";

export default {
  name: "tool-detail",
  components: {
    AddToCart,
  },
  data() {
    return {
      tool: {},
    };
  },
  methods: {
    getTool(id) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tools/${id}`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
        },
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
        })
        .then((data) => {
          this.tool = data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  created() {
    this.getTool(this.$route.params.id);
  },
};
</script>

<style></style>
