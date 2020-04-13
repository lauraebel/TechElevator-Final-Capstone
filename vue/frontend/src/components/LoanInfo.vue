<template>
  <div class="loan-info">
    <h1 class="tool-name">{{loan.tool.toolName}}</h1>
    <img v-bind:src="require(`@/assets/images/product-img/${loan.tool.toolImgName}`)" class="tool-img"/>
    <p class="loaned-on">Loaned On: {{loan.dateLoaned.month}} {{loan.dateLoaned.dayOfMonth}}, {{loan.dateLoaned.year}}</p>
    <p class="due-on">Due: {{loan.dueDate.month}} {{loan.dueDate.dayOfMonth}}, {{loan.dueDate.year}}</p>
  </div>
</template>

<script>
export default {
  name: "loan-info",
  props: {
    loan: Object
  }
}
</script>

<style>

</style>