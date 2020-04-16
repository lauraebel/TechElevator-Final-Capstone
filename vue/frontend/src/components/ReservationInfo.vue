<template>
  <div class="reservation-info">
    <h1 class="tool-name">{{ tool.toolName }}</h1>
    <img
      v-bind:src="
        require(`@/assets/images/product-img/${tool.toolImgName}`)
      "
      class="tool-img"
    />
    <p class="description">
       {{ tool.toolDescription }}
    </p>
  </div>
</template>

<script>
import auth from '../auth'

export default {
  name: "reservation-info",
  props: {
    reservation: Object,
  },
  data() {
    return {
      tool: {}
    };
  },
  methods: {
    getTool() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tools/${this.reservation.toolId}`,
        {
          headers: {
            Authorization: `Bearer ${auth.getToken()}`,
          },
        }
      )
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
    this.getTool();
  },
};
</script>
