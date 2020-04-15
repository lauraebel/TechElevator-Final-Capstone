<template>
  <div class="add-to-cart">
    <span v-if="isAvailable" v-on:click="clickedCart" class="add">Add to Cart</span>
    <span v-else v-on:click="clickedReserve" class="reserve">Reserve Tool</span>
    <div class="icon">
      <img
        v-if="isAvailable"
        src="@/assets/images/icons/add-to-cart.png"
        class="add-to-cart-icon"
      />
      <img v-else src="@/assets/images/icons/not-available.png" class="not-available-icon"/> 
    </div>

    <!-- <button v-if="!isAvailable" :disabled='isDisabled'><img src="@/assets/images/icons/add-to-cart.png" class="can-not-add-to-cart-icon" /></button>
        <button v-if="inCart"><img src="@/assets/images/icons/in-cart.png" class="mobile-in-cart-icon" /><img src="@/assets/images/icons/desktop-in-cart.png" class="desktop-in-cart-icon" /></button> -->
  </div>
</template>

<script>
import auth from "../auth";

export default {
  name: "add-to-cart",
  props: {
    tool: Object,
    isAvailable: Boolean
  },
  data() {
    return {
      cart: {}
    };
  },
  methods: {
    getCart() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/cart/${auth.getUser().sub}`,
        {
          headers: {
            Authorization: `Bearer ${auth.getToken()}`
          }
        }
      )
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cart = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    clickedCart() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/cart/add/${this.cart.id}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ toolId: this.tool.toolId })
      })
        .then(response => {
          if (response.ok) {
            this.cart.items.push(this.tool);
            this.disabled = true;
          }
        })
        .catch(err => console.error(err));
    },
    clickedReserve() {
      fetch
    }
  },
  computed: {
    inCart() {
      if (this.cart.items.length === 0) {
        return false;
      } else {
        const items = this.cart.items;
        let added = false;

        items.forEach(item => {
          if (item.toolId === this.tool.toolId) {
            added = true;
          }
        });

        return added;
      }
    }
  },
  created() {
    this.getCart();
  }
};
</script>
