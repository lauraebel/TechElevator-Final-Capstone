<template>
  <div class="tool-search">
    <h1>Tool Search</h1>

    <div class="tools">
      <div class="tool" v-for="tool in filteredTools" v-bind:key="tool.toolId">{{tool.toolName}}</div>
    </div>

    
    <!-- dropdown for brand -->
    <!-- dropdown for category  -->
    <!-- button to show/hide keyword input -->
    <!-- hidden keyword input field -->
  </div>
</template>

<script>
export default {
  name: 'tool-search',
  data() {
    return {
      apiURL: "https://5e8dd4e822d8cd0016a79b3f.mockapi.io/",
      allTools: [],
      allBrands: [],
      allCategories: [],
      brand: -1,
      category: -1,
      keyword: ''
    }
  },
  methods: {
    getAllTools(){
      fetch(this.apiURL + "/tools")
                .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.allTools = data;
                })
                .catch( err => { console.error(err) });
    },
    //method to get brands
    getBrands(){
      fetch(this.apiURL + "/brands")
        .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.allBrands = data;
                })
                .catch( err => { console.error(err) });
    },
    //method to get categories
    getCategories() {
      fetch(this.apiURL + "/categories")
        .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.allCategories = data;
                })
                .catch( err => { console.error(err) });
    },
    //method to get list of matching tools
    matchesBrand(tool){
      if (tool.toolBrand === this.brand){
        return true; 
      } else {
        return false;
      }
    },
    matchesCategory(tool){
      const categoryList = tool.toolCategories;

      if (this.category > 0){
        for (let i = 0; i < categoryList.length; i++){
          if (category === categoryList[i]){
            return true;
          }
        }

        return false;
      } else {
        return false;
      }
    },
    containsKeyword(tools){
      const filter = new RegExp(this.keyword, "i");

      return tools.filter( tool => {
        const name = tool.toolName;
        const description = tool.toolDescription;

        return (name.match(filter) || description.match(filter));
      })
    },
    inList(tool, toolList){
      const id = tool.toolId;
      for (let i = 0; i < toolList.length; i++){
        const arrayToolId = toolList[i];
        if (id === arrayToolId){
          return true;
        }
      }
      return false;
    },
    getMatchingTools(){
      if (this.brand=== -1 && this.category === -1 && this.keyword === ''){
        return this.allTools;
      }

      let filtered = [];
      
      if (this.keyword !== ''){
        filtered = containsKeyword(allTools);
      } 
      
      this.allTools.forEach(tool => {
        if (this.brand > 0 && this.matchesBrand(tool)){
          if (!this.inList(tool, filtered)){
            filtered.push(tool);
          }
        }
        if (this.category > 0 && this.matchesCategory(tool)){
          if (!this.inList(tool, filtered)){
            filtered.push(tool);
          }
        }
      });
      return filtered;
    }
  },
  computed: {
    filteredTools(){
      return this.getMatchingTools();
    }
  }, 
  created() {
    this.getAllTools();
    this.getBrands();
    this.getCategories();
  }
}
</script>