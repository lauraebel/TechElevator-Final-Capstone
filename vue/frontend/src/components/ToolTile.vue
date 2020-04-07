<template>
  <div class="tool">
      <h1>{{tool.name}}</h1>
      <img v-bind:src="('@/assets/images/' + tool.img_name)" />
  </div>
</template>

<script>
export default {
    name: 'tool-tile',
    props: {
        name: String,
        image_path: String
    },
    data() {
        
    },
}
</script>

<style>
    
</style>