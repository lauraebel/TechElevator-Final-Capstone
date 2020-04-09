<template>
  <div class="tool-search">
    <h1>Tool Search</h1>
    <div class="filters">
      <button v-on:click="resetSearch()">Reset Search</button>
      <v-select placeholder="Filter by Brand" label="brandName" v-model="brand" :options="allBrands" :reduce="brandName => brandName.brandId" ></v-select>
      <v-select placeholder="Filter by Category" label="categoryName" v-model="category" :options="allCategories" :reduce="categoryName => categoryName.categoryId"></v-select>  
    </div>

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
      brand: '',
      category: '',
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
    resetSearch(){
      this.brand = '';
      this.category = '';
      this.keyword = '';
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
      const selectedCategory = this.category;

      // console.log(tool.toolName);
      // console.log('Tool category is' + category);
      // console.log('Selected category is' + selectedCategory);
      // console.log(category === selectedCategory);
      
      tool.toolCategories.forEach(category => {
        if (category === selectedCategory){
          return true;
        }
      });
    },
    containsKeyword(tools){
      const filter = new RegExp(this.keyword, "i");

      return tools.filter( tool => {
        const name = tool.toolName;
        const description = tool.toolDescription;

        return (name.match(filter) || description.match(filter));
      });
    },
    getMatchingTools(){
      if (this.brand === '' && this.category === '' && this.keyword === ''){
        return this.allTools;
      }

      let filtered = new Set();
      
      if (this.keyword !== ''){
        filtered = containsKeyword(allTools);
      } 
      
      this.allTools.forEach(tool => {
        console.log(tool.toolName);
        console.log(this.matchesCategory(tool));
        if (this.brand !== '' && this.matchesBrand(tool)){ 
          filtered.add(tool);
        }
        if (this.category !== '' && this.matchesCategory(tool)){
          filtered.add(tool);
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