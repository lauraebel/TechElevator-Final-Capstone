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
        <span v-if="onlyAvailable">Available Tools</span>
        <span v-else>All Tools</span>
        <toggle-button v-model="onlyAvailable" color="#FFD58E" />
      </div>
      <v-select
        placeholder="Filter by Brand"
        label="brandName"
        v-model="brand"
        :options="allBrands"
        :reduce="brandName => brandName.brandId"
      ></v-select>
      <v-select
        placeholder="Filter by Category"
        label="categoryName"
        v-model="category"
        :options="allCategories"
        :reduce="categoryName => categoryName.categoryId"
      ></v-select>
    </div>

    <div class="tools" >
      <div class="tool" v-for="tool in filteredTools" v-bind:key="tool.toolId">
        <tool-tile class="tool" v-bind:tool="tool" />
        <div class="add" v-on:click="clickedCart(tool.toolId)">
          <span class="add" >Add to Cart</span>
          <add-to-cart/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ToolTile from "../components/ToolTile";
import auth from '../auth';

export default {
  name: "tool-search",
  components: {
    ToolTile
  },
  data() {
    return {
      user: {},
      username: "",
      allTools: [],
      availableTools: [],
      allBrands: [],
      allCategories: [],
      brand: "",
      category: "",
      keyword: "",
      onlyAvailable: false,
      userCart: {}
    };
  },
  methods: {
    getTools() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tools`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.allTools = data;
        })
        .catch(err => {
          console.error(err);
        });
      fetch( `${process.env.VUE_APP_REMOTE_API}/api/available`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
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
    getBrands() {
      fetch( `${process.env.VUE_APP_REMOTE_API}/api/brands`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
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
    getCategories() {
      fetch( `${process.env.VUE_APP_REMOTE_API}/api/categories`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
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
    },
    clickedCart(toolId){
      this.$emit('clickedAddToCart', toolId);
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
    this.getCart();
    this.user = auth.getUser();
  }
};
</script>
