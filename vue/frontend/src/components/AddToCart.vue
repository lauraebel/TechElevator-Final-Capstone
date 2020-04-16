<template>
  <div class="add-to-cart">
    <span
      v-if="!isAvailable && !inCart && !reserved"
      v-on:click="clickedReserve"
      class="add"
      >Reserve Tool</span
    >
    <span
      v-if="!isAvailable && !inCart && reserved"
      v-on:click="clickedReserve"
      class="add"
      >Reserved</span
    >
    <span v-if="isAvailable && !inCart" v-on:click="clickedCart" class="add"
      >Add to Cart</span
    >
    <span v-if="inCart" :disabled="isDisabled" class="add"
      >In your Cart!</span
    >

    <div class="icon">
      <img
        v-if="!isAvailable && !inCart"
        src="@/assets/images/icons/not-available.png"
        class="not-available-icon"
      />
      <img
        v-if="isAvailable && !inCart"
        src="@/assets/images/icons/add-to-cart.png"
        class="add-to-cart-icon"
      />
      <img
        v-if="inCart"
        src="@/assets/images/icons/in-cart.png"
        class="in-cart-icon"
      />
    </div>
  </div>
</template>

<script>
import auth from "../auth";

export default {
  name: "add-to-cart",
  props: {
    tool: Object,
  },
  data() {
    return {
      cart: {
        items: [],
      },
      isAvailable: true,
      inCart: false,
      isDisabled: false,
      reserved: false
    };
  },
  methods: {
    getCart() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/cart/${auth.getUser().sub}`,
        {
          headers: {
            Authorization: `Bearer ${auth.getToken()}`,
          },
        }
      )
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
        })
        .then((data) => {
          this.cart = data;
          this.checkCart();
        })
        .catch((err) => {
          console.error(err);
        });
    },
    checkCart() {
      const items = this.cart.items;
      if (items.length === 0) {
        this.inCart = false;
      } else {
        items.forEach((item) => {
          if (item.toolId === this.tool.toolId) {
            this.inCart = true;
          }
        });
      }
    },
    clickedCart() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/cart/add/${this.cart.id}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ toolId: this.tool.toolId }),
      })
        .then((response) => {
          if (response.ok) {
            this.inCart = true;
            this.isDisabled = true;
          }
        })
        .catch((err) => console.error(err));
    },
    clickedReserve() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/reservations/add/${this.cart.id}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${auth.getToken()}`,
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ toolId: this.tool.toolId }),
        }
      )
        .then((response) => {
          if (response.ok) {
            this.isDisabled = true;
            this.reserved = true;
          }
        })
        .catch((err) => console.error(err));
    },
    checkAvailability() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/isToolAvailable?toolId=${this.tool.toolId}`,
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
          this.isAvailable = data;
        })
        .catch((err) => console.error(err));
    },
  },
  created() {
    this.getCart();
    this.checkAvailability();
  },
};
</script>
