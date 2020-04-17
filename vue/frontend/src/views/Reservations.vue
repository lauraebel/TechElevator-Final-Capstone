<template>
  <div class="user-reservations">
    <h1 class="page-title">My Reservations</h1>
    <div class="no-loans" v-if="noReservations" >No current reservations.</div>
    <reservation-info
      v-for="reservation in reservations"
      v-bind:key="reservation.id"
      v-bind:reservation="reservation"
    />
  </div>
</template>

<script>
import auth from "../auth";
import ReservationInfo from "../components/ReservationInfo";

export default {
  name: "reservations",
  components: {
    ReservationInfo,
  },
  data() {
    return {
      reservations: [],
      noReservations: true
    };
  },
  methods: {
    getReservations() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/reservations/${
          auth.getUser().sub
        }`,
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
          this.reservations = data;
          if (this.reservations.length !== 0){
            this.noReservations = false;
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  created() {
    this.getReservations();
  },
};
</script>
