<template>
  <div class="menu">
    <button v-on:click="clickedMenu"><img src="@/assets/images/icons/mobile-menu.png" class="mobile-menu-icon" /><img src="@/assets/images/icons/menu.png" class="menu-icon" /></button>
  </div>
</template>

<script>
export default {
  name: "menu-icon",
  data (){
    return {
      hidden: true
    }
  }, 
  methods: {
    clickedMenu() {
      this.hidden = !this.hidden;
      this.$emit('clickedMenu', this.hidden);
    }
  }
};
</script>
