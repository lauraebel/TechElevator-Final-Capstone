<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <label for="firstname" class="sr-only">First Name</label>
      <input
        type="text"
        id="firstname"
        class="form-control"
        placeholder="First Name"
        v-model="user.firstname"
        required
        autofocus
      />
      <label for="lastname" class="sr-only">Last Name</label>
      <input
        type="text"
        id="lastname"
        class="form-control"
        placeholder="Last Name"
        v-model="user.lastname"
        required
        autofocus
      />
      <label for="licenseno" class="sr-only">Driver License Number</label>
      <input
        type="text"
        id="licenseno"
        class="form-control"
        placeholder="Driver License Number"
        v-model="user.licenseno"
        required
        autofocus
      />
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <router-link :to="{ name: 'login' }">
        Have an account?
      </router-link>
    </form>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      user: {
        firstname: "",
        lastname: "",
        licenseno: "",
        username: "",
        password: "",
        confirmPassword: "",
        roleID: 2
      },
      registrationErrors: false
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({
              path: "/login",
              query: { registration: "success" }
            });
          } else {
            this.registrationErrors = true;
          }
        })

        .then(err => console.error(err));
    }
  }
};
</script>

<style></style>
