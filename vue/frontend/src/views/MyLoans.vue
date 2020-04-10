<template>
    <div class="user-loans">
    <h1>My Loans</h1>
    <loan-info />
    <div class="loan" v-for="loan in loans" v-bind:key="loan.loanId">{{loan.tool}}</div>
    </div>

</template>

<script>
import LoanInfo from "../components/LoanInfo";

export default {
  name: 'user-loans',
  components: {
      LoanInfo
  },
  data() {
        return {
            apiURL: "http://localhost:8080/AuthenticationApplication/api/loans",
            allLoans: [],
            returnedOn: '',
            id: 1
        }
    },
  methods: {
    getLoans() {
        fetch(this.apiURL + "/loans")
        .then(response => {
        return response.json();
        })
        .then(data => {
        this.allLoans = data;
        })
        .catch(err => {console.error(err)});
        }
    }
//   computed: {
//     currentLoans() {
//         let loanList;

//         if(this.returnedOn == null) {
//             loanList = this.allLoans;
//         }
//     },
//     created() {
//         this.getLoans();
//     }
//   }
 }
</script>