<template>
	<button class="checkout clickable" v-on:click="checkout">{{message}}</button>
</template>

<script>
import auth from '../auth';

export default {
  name: "checkout",
  data() {
    return {
      message: 'Check Out'
    }
  },
  methods: {
    checkout() {
		fetch(`${process.env.VUE_APP_REMOTE_API}/api/cart/checkout/${auth.getUser().sub}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        }
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({
              path:'/loans',
              query: {checkout: "success"}
            });
          }
        })
        .catch(err => console.error(err));
	}
  }
};
</script>

<style></style>
