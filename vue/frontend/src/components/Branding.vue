<template>
  <div class="branding">
      <router-link :to="{name: 'home'}">
        <span class="title">Tool Library</span>
        <img src="@/assets/images/logo/mobile-logo.png" class="mobile-logo" />
        <img src="@/assets/images/logo/logo.png" class="logo" />
      </router-link>
    </div>
</template>

<script>
export default {
	name: 'branding'
}
</script>

<style>

</style>