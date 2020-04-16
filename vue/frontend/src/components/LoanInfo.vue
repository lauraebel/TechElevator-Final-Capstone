<template>
  <div class="loan-info">
    <h1 class="tool-name">{{loan.tool.toolName}}</h1>
    <img v-bind:src="require(`@/assets/images/product-img/${loan.tool.toolImgName}`)" class="tool-img"/>
    <p class="dates">Loaned On: {{getDate(loan.dateLoaned)}}<br />Due: {{getDate(loan.dueDate)}}</p>
    <return-tool v-bind:loan="loan" />
  </div>
</template>

<script>
import ReturnTool from "../components/ReturnTool";
export default {
  name: "loan-info",
  components: {
    ReturnTool
  },
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