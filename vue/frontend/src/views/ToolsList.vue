<template>
  <div class="tools">
    <h1>Available Tools</h1>
    <tool-tile v-for="tool in tools" :key="tool.toolId" v-bind:apiUrl="TOOLS_API"/>
  </div>
</template>

<script>
import ToolTile from '../components/ToolTile'

export default {
  name: 'tools',
  components: {
    ToolTile
  },
  data(){
    return {
      tools: []
    }
  },
  methods: {
    getAllTools(){
      fetch(process.env.TOOLS_API + "/available")
                .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.tools = data;
                })
                .catch( err => { console.error(err) });
    }
  },
  created() {
    this.getAllTools();
  }
}
</script>

<style>

</style>