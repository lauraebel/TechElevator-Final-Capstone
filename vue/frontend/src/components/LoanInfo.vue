<template>
  <div class="loan-info">
    <h1 class="tool-name">{{loan.tool.toolName}}</h1>
    <img v-bind:src="require(`@/assets/images/product-img/${loan.tool.toolImgName}`)" class="tool-img"/>
    <p class="loaned-on">Loaned On: {{getDate(loan.dateLoaned)}}</p>
    <p class="due-on">Due: {{getDate(loan.dueDate)}}</p>
  </div>
</template>

<script>
export default {
  name: "loan-info",
  props: {
    loan: Object
  },
  methods: {
    getDate(javaDate){
      const day = javaDate.dayOfMonth;
      const month = javaDate.month.toString().toLowerCase();
      const year = javaDate.year;
      const date = month + " " + day + ", " + year;

      return date;
    }
  }
}
</script>

<style>

</style>