<template>
  <div class="tool-search">
    <h1>Tool Search</h1>
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
      brands: [],
      categories: [],
      brand: -1,
      category: -1,
      keyword: ''
    }
  },
  computed: {
    filteredTools(){
      return getMatchingTools();
    }
  }, 
  methods: {
    //method to get brands
    getBrands(){
      fetch(process.env.MOCK_API + "/brands")
        .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.brands = data;
                })
                .catch( err => { console.error(err) });
    },
    //method to get categories
    getCategories() {
      fetch(process.env.MOCK_API + "/categories")
        .then( response => {
                    return response.json();
                })
                .then( data => {
                    this.categories = data;
                })
                .catch( err => { console.error(err) });
    },
    //method to get list of matching tools
    getMatchingTools(){
      if (brand > 0){

      }
    }

  },
  created() {
    this.getBrands();
    this.getCategories();


  }
}
</script>

<style>

</style>