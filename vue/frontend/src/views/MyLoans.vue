<template>
    <div class="user-loans">
    <h1>My Loans</h1>
    
    <div class="loan" v-for="loan in loans" v-bind:key="loan.loanId">{{loan.tool}}</div>
    </div>

</template>

<script>
export default {
  name: 'user-loans',
  data() {
        return {
            apiURL: "https://5e8dd4e822d8cd0016a79b3f.mockapi.io/",
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