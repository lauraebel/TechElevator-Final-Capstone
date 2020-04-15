<template>
  <div class="user-loans">
    <h1 class="page-title">My Loans</h1>
    <!-- <div v-for="loan in loans" v-bind:key="loan.loanId">{{loan.loanId}}</div> -->
    <loan-info v-for="loan in loans" v-bind:key="loan.loanId" v-bind:loan="loan" />
  </div>
</template>

<script>
import auth from '../auth';
import LoanInfo from '../components/LoanInfo';

export default {
  name: "user-loans",
  components: {
      LoanInfo
  },
  data() {
    return {
      loans: []
    };
  },
  methods: {
    getMockLoans(){
      fetch("https://5e8dd4e822d8cd0016a79b3f.mockapi.io/loans")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.loans = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    getLoans() {
        fetch(`${process.env.VUE_APP_REMOTE_API}/api/loans/${auth.getUser().sub}`, {
        headers: {
          Authorization: `Bearer ${auth.getToken()}`
        }
      })
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.loans = data;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
      // real data
      this.getLoans();

      //mock data
      // this.getMockLoans();
  }
};
</script>

<style></style>
