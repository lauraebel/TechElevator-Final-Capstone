<template>
	<button class="return clickable" v-on:click="returnTool">Return Tool</button>
</template>

<script>
import auth from '../auth';

export default {
  name: "return",
  props: {
    loan: Object
  },
  methods: {
    returnTool() {
		fetch(`${process.env.VUE_APP_REMOTE_API}/api/loans/return`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${auth.getToken()}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        }, 
        body: JSON.stringify({"loanId": this.loan.loanId, "userId": this.loan.userId, "tool": this.loan.tool})
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