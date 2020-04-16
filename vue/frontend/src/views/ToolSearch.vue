<template>
  <div class="tool-search">
    <h1 class="page-title">Tool Search</h1>
    <div class="filters">
      <button v-on:click="resetSearch()" class="reset">Clear Filters</button>
      <input
        type="text"
        placeholder="Search by Keyword"
        v-model="keyword"
        class="keyword" v-on:keyup="filteredTools"
      />
      <div class="toggle">
        <span v-if="onlyAvailable">Available Tools</span>
        <span v-else>All Tools</span>
        <toggle-button :options="allAvailable" v-model="onlyAvailable" color="#FFD58E" v-on:change="filteredTools"/>
      </div>
      <v-select
        placeholder="Brand"
        label="brandName"
        v-model="brand"
        :options="allBrands"
        :reduce="(brandName) => brandName.brandId"
        v-on:input="filteredTools"
      ></v-select>
      <v-select
        placeholder="Category"
        label="categoryName"
        v-model="category"
        :options="allCategories"
        :reduce="(categoryName) => categoryName.categoryId"
        :clearable="true"
        v-on:input="filteredTools"
      ></v-select>
    </div>

    <div class="tools">
      <div class="tool" v-for="tool in tools" v-bind:key="tool.toolId">
        <tool-tile v-bind:tool="tool" />
        <add-to-cart
          v-bind:tool="tool"
          v-bind:isAvailable="onlyAvailable"
        />
      </div>
    </div>
  </div>
</template>

<script>
import ToolTile from "../components/ToolTile";
import AddToCart from "../components/AddToCart";
import auth from "../auth";

export default {
  name: "tool-search",
  components: {
    ToolTile,
    AddToCart,
  },
  data() {
    return {
      username: "",
      tools: [],
      toolsWithKeyword: [],
      allBrands: [],
      allCategories: [],
      brand: null,
      category: null,
      keyword: "",
      onlyAvailable: false,
    };
  },
  methods: {
    getMockData() {
      fetch(`https://5e8dd4e822d8cd0016a79b3f.mockapi.io/tools`)
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allTools = data;
        })
        .catch((err) => {
          console.error(err);
        });
      fetch(`https://5e8dd4e822d8cd0016a79b3f.mockapi.io/available`)
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.availableTools = data;
        })
        .catch((err) => {
          console.error(err);
        });

      fetch(`https://5e8dd4e822d8cd0016a79b3f.mockapi.io/brands`)
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allBrands = data;
        })
        .catch((err) => {
          console.error(err);
        });

      fetch(`https://5e8dd4e822d8cd0016a79b3f.mockapi.io/categories`)
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allCategories = data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getTools(onlyAvailable, category, brand) {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tools/filtered?onlyAvailable=${onlyAvailable}&categoryId=${category}&brandId=${brand}`,
        {
          headers: {
            Authorization: `Bearer ${auth.getToken()}`,
          },
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.tools = data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getBrands() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/brands`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
        },
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allBrands = data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getCategories() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/categories`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
        },
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.allCategories = data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getAvailable() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/available`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
        },
      })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.allAvailable = data;
      })
      .catch((err) => {
        console.error(err);
      });
    },
    resetSearch() {
      this.brand = null;
      this.category = null;
      this.keyword = null;
    },
    containsKeyword() {
      const filter = new RegExp(this.keyword, "i");

      return this.tools.filter((tool) => {
        const name = tool.toolName;
        const description = tool.toolDescription;

        return name.match(filter) || description.match(filter);
      });
    },
    filteredTools() {
      if (this.brand === null && this.category === null && this.keyword === ''){
        this.getTools(this.onlyAvailable, 0, 0);
      } 
      else {
        let brandId = 0;
        let categoryId = 0;

        if (this.brand !== null) {
          brandId = this.brand;
        }

        if (this.category !== null) {
          categoryId = this.category;
        }

        this.getTools(this.onlyAvailable, categoryId, brandId);

        if (this.keyword !== ""){
          this.tools = this.containsKeyword();
        }
      }
    },
  },
  created() {
    // real data
    this.getTools(this.onlyAvailable, 0, 0);
    this.getBrands();
    this.getCategories();

    // mock data
    // this.getMockData();
  },
};
</script>
