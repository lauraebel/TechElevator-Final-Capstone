<template>
  <div class="tool-search">
    <h1 class="page-title">Tool Search</h1>
    <div class="filters">
      <button v-on:click="resetSearch()" class="reset">Clear Filters</button>
      <input
        type="text"
        placeholder="Search by Keyword"
        v-model="keyword"
        class="keyword"
      />
      <div class="toggle">
        <span v-if="onlyAvailable">Available Tools</span
        ><span v-else>All Tools</span>
        <toggle-button v-model="onlyAvailable" color="#FFD58E" />
      </div>
      <v-select
        placeholder="Filter by Brand"
        label="brandName"
        v-model="brand"
        :options="allBrands"
        :reduce="brandName => brandName.brandId"ß
      ></v-select>
      <v-select
        placeholder="Filter by Category"
        label="categoryName"
        v-model="category"
        :options="allCategories"
        :reduce="categoryName => categoryName.categoryId"
      ></v-select>
    </div>

    <div class="tools">
      <tool-tile
        class="tool"
        v-for="tool in filteredTools"
        v-bind:key="tool.toolId"
        v-bind:tool="tool"
      />
    </div>
  </div>
</template>

<script>
import ToolTile from "../components/ToolTile";

export default {
  name: "tool-search",
  components: {
    ToolTile
  },
  data() {
    return {
      apiURL: "https://5e8dd4e822d8cd0016a79b3f.mockapi.io",
      allTools: [],
      availableTools: [],
      allBrands: [],
      allCategories: [],
      brand: "",
      category: "",
      keyword: "",
      onlyAvailable: false
    };
  },
  methods: {
    getTools() {
      fetch(this.apiURL + "/tools")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.allTools = data;
        })
        .catch(err => {
          console.error(err);
        });
      fetch(this.apiURL + "/available")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.availableTools = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //method to get brands
    getBrands() {
      fetch(this.apiURL + "/brands")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.allBrands = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //method to get categories
    getCategories() {
      fetch(this.apiURL + "/categories")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.allCategories = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    resetSearch() {
      this.brand = "";
      this.category = "";
      this.keyword = "";
    },
    //method to get list of matching tools
    matchesBrand(tool) {
      if (tool.toolBrand === this.brand) {
        return true;
      } else {
        return false;
      }
    },
    matchesCategory(tool) {
      const categoryList = tool.toolCategories;
      const selectedCategory = this.category;
      let matches = false;

      categoryList.forEach(category => {
        if (category === selectedCategory) {
          matches = true;
        }
      });
      return matches;
    },
    containsKeyword(tools) {
      const filter = new RegExp(this.keyword, "i");

      return tools.filter(tool => {
        const name = tool.toolName;
        const description = tool.toolDescription;

        return name.match(filter) || description.match(filter);
      });
    }
  },
  computed: {
    filteredTools() {
      let toolList;

      if (this.onlyAvailable) {
        toolList = this.availableTools;
      } else {
        toolList = this.allTools;
      }

      if (this.brand === "" && this.category === "" && this.keyword === "") {
        return toolList;
      }

      let filtered = new Set();

      if (this.keyword !== "") {
        filtered = this.containsKeyword(toolList);
      }

      toolList.forEach(tool => {
        if (this.brand !== "" && this.category !== "") {
          if (this.matchesBrand(tool) && this.matchesCategory(tool)) {
            filtered.add(tool);
          }
        } else if (this.brand !== "" && this.category == "") {
          if (this.matchesBrand(tool)) {
            filtered.add(tool);
          }
        } else {
          if (this.matchesCategory(tool)) {
            filtered.add(tool);
          }
        }
      });

      return filtered;
    }
  },
  created() {
    this.getTools();
    this.getBrands();
    this.getCategories();
  }
};
</script>
