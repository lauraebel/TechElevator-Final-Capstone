<template>
  <div class="user-reservations">
    <h1 class="page-title">My Reservations</h1>
    <reservation-info v-for="reservation in reservations" 
        v-bind:key="reservation.id" v-bind:reservation="reservation" />
  </div>
</template>

<script>
import auth from '../auth';
import ReservationInfo from '../components/ReservationInfo';

export default {
  name: "reservations",
  components: {
      ReservationInfo
  },
  data() {
      return {
          reservations: []
      };
  },
  methods: {
      getReservations() {
          fetch(`${process.env.VUE_APP_REMOTE_API}/api/reservations/${auth.getUser().sub}`, {
          headers: {
              Authorization: `Bearer ${auth.getToken()}`
          }
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.reservations = data;
            })
            .catch(err => {
                console.error(err);
            });
      }
  },
  created() {
      this.getReservations();
  }
}
</script>