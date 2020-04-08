<template>
  <div class="tool">
      <router-link :to="{name:'tool', params: {id: tool.toolId}}" >
        <h1 class="tool-name">{{tool.name}}</h1>
        <img v-bind:src="('@/assets/images/' + tool.img_name)" class="tool-img"/>
      </router-link>
  </div>
</template>

<script>
export default {
    name: 'tool-tile',
    props: {
        apiUrl: String,
        id: Number
    },
    data() {
        tool: {}
    },
    methods: {
        getTool(id){
            fetch(this.apiUrl + "/" + id)
                .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.tool = data;
                })
                .catch( err => { console.error(err) });
        }
    }, 
    created(){
        this.getTool(this.id)();
    }
}
</script>

<style>
    
</style>