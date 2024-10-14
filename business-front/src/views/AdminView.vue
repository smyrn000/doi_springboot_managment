<script>
// eslint-disable-next-line no-unused-vars
import { useRouter } from 'vue-router'
export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  methods: {
    // logs user out
    logout() {
      localStorage.clear()
      this.router.push('/auth/login')
    }
  }
}
</script>

<template>
  <div class="home-view">
    <h2>Σελίδα Διαχειριστή</h2>    
    <div class="button-container">
      <button @click="this.router.push('/admin/register')"><h3>Δημιουργία Χρήστη</h3></button>
    </div>
    <div class="button-container">
      <button @click="this.router.push('/admin/modify')"><h3>Τροποποίηση Χρήστη</h3></button>
    </div>
    <div class="button-container">
      <button @click="logout()"><h3>Έξοδος</h3></button>
    </div>
  </div>
</template>

<style>
.home-view {
  width: 30rem;
  background-color: #f1f1f1;
  box-shadow: 0 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
}
h2 {
  text-align: center;
  padding: 1rem 0;
}
.button-container {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 1rem 0;
}
button {
  width: 70%;
  padding: 0.625rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}
</style>
