<template>
	<button class="return clickable" v-on:click="returnTool">Return Tool</button>
</template>

<script>
import auth from '../auth';

export default {
  name: "return",
  methods: {
    returnTool() {
		fetch(`${process.env.VUE_APP_REMOTE_API}/api/loans/return/${auth.getUser().sub}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        }
      })
        .then(response => {
          if (response.ok) {
            this.$router.go('/success');
          }
        })
        .catch(err => console.error(err));
	}
  }
};
</script>