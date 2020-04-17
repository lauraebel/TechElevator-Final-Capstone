<template>
  <div class="tool-details">
    <h1 class="page-title">Tool Details</h1>
    <reservation-info
      v-for="tool in tools"
      v-bind:key="tool.id"
      v-bind:tool="tool"
    />
  </div>
</template>

<script>
import ToolDetails from "../components/ToolDetails";

export default {
  name: 'tool-view',
    components: {
    ToolDetails,
  },
    data() {
    return {
      tool: "",
    };
  },
  methods: {
    getReservations() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tools/${
          auth.getUser().sub
        }`,
        {
          headers: {
            Authorization: `Bearer ${auth.getToken()}`,
          },
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allTools = data;
        })
        .catch((err) => {
          console.error(err);
        });
  }
  }
</script>

<style>

</style>