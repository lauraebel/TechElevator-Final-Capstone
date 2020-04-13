<template>
  <div class="loan-view" v-if="this.loan.id != null">
    <router-link :to="{name: 'loan', params: {id: this.loan.loanId }}" class="loan-info">
    <h1 class="tool-name">{{ tool.toolName }}</h1>
    </router-link>
  </div>
</template>

<script></script>

<style></style>