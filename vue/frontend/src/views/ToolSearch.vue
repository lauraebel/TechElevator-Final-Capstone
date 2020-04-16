<template>
  <div class="tool-search">
    <h1 class="page-title">Tool Search</h1>
    <div class="filters">
      <input
        type="text"
        placeholder="Search by Keyword"
        v-model="keyword"
        class="keyword" 
      />
      <div class="toggle">
        <span v-if="onlyAvailable">Available Tools</span>
        <span v-else>All Tools</span>
        <toggle-button v-model="onlyAvailable" color="#FFD58E" v-on:change="checkCategoryAndBrand"/>
      </div>
      <v-select
        placeholder="Brand"
        label="brandName"
        v-model="brand"
        :options="allBrands"
        :reduce="(brandName) => brandName.brandId"
        v-on:input="checkCategoryAndBrand"
      ></v-select>
      <v-select
        placeholder="Category"
        label="categoryName"
        v-model="category"
        :options="allCategories"
        :reduce="(categoryName) => categoryName.categoryId"
        :clearable="true"
        v-on:input="checkCategoryAndBrand"
      ></v-select>
    </div>

    <div class="tools">
      <div class="tool" v-for="tool in filteredTools" v-bind:key="tool.toolId">
        <tool-tile v-bind:tool="tool" />
        <add-to-cart
          v-if="containsKeyword"
          v-bind:tool="tool"
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
    containsKeyword(tool) {
      if (this.keyword !== ''){
        return true;
      } else {
        const filter = new RegExp(this.keyword, "i");
        const name = tool.toolName;
        const description = tool.toolDescription;
        return name.match(filter) || description.match(filter);
      }
    },
    checkCategoryAndBrand() {
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
      }
    },
  },
  computed: {
    filteredTools(){
      if (this.keyword === ''){
        return this.tools;
      } else {
        return this.tools.filter(tool => {
          const filter = new RegExp(this.keyword, "i");
          const name = tool.toolName;
          const description = tool.toolDescription;
          return name.match(filter) || description.match(filter);
        })
      }
    }
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
