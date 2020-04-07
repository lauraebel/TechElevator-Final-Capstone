<template>
  <div class="tool">
      <h1>{{tool.name}}</h1>
      <img v-bind:src="('@/assets/images/' + tool.img_name)" />
  </div>
</template>

<script>
export default {
    props: {
        // toolName: String,
        // toolDescription: String,
        // toolImgName: String,
        // toolBrand: String,
    },
    data() {
        return {
            tool: {
                name: 'Hammer',
                description: 'A really cool hammer.',
                img_name: 'hammer.jpg',
                brand: 'Black & Decker'
            }
        }
    },
    computed: {},
    methods(){},
    created(){

    }
}
</script>

<style>
    div.tool {
        background-color: #FFD58E;
    }

</style>