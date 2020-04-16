<template>
  <div class="reservation-info">
    <h1 class="tool-name">{{reservation.tool.toolName}}</h1>
    <img v-bind:src="require(`@/assets/images/product-img/${reservation.tool.toolImgName}`)" class="tool-img"/>
    <p class="description">Description: {{reservation.tool.toolDescription}}</p>
  </div>
</template>

<script>
export default {
  name: "reservation-info",
  props: {
    reservation: Object
  }
}
</script>