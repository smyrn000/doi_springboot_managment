<script>
import { useRouter } from 'vue-router'
export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      username: '',
      full_name: '',
      AFM: '',
      email: '',
      address: '',
      phone_num: '',
      errorMessage: 'Δέν είναι δυνατή η εύρεση χρήστη',
      hasData: false,
      hasApp: false
    }
  },
  methods: {
    // retrieves logged in user data
    async retrieveUserData() {
      const token = localStorage.getItem('token')
      this.username = localStorage.getItem('username')
      if (!token || !this.username) {
        this.hasData = false
        return
      }
      const response = await fetch(`http://localhost:8080/user/${this.username}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (response.ok) {
        this.hasData = true
      }
      const userData = await response.json()
      this.full_name = userData.full_name
      this.AFM = userData.afm
      this.email = userData.email
      this.address = userData.address
      this.phone_num = userData.phone_num
    },    
    // method to check if user already sumbitted the form
    async checkFormSubmission() {
      const token = localStorage.getItem('token')      
      if (!token) {
        return
      }
      const response = await fetch(`http://localhost:8080/user/${this.username}/hasSubmittedForm`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (response.ok) {
        const hasSubmitted = await response.json()
        console.log(hasSubmitted)
        this.hasApp = hasSubmitted
      }
    },
    // method to log out the user
    logout() {
      localStorage.clear()
      this.router.push('/auth/login')
    }
  },
  beforeMount() {
    this.retrieveUserData()
  },
  mounted() {
    this.checkFormSubmission();
  }
  
}
</script>

<template>
  <div class="details">
    <h2>Στοιχεία Νόμιμου Εκπροσώπου</h2>
    <div v-if="hasData">
      <div class="label">
        <h3>Όνοματεπώνυμο:</h3>
        <h3 style="font-weight: bold">{{ full_name }}</h3>
      </div>
      <div class="label">
        <h3>Εταιρικό ΑΦΜ:</h3>
        <div v-if="AFM">
          <h3 style="font-weight: bold">{{ AFM }}</h3>
        </div>
        <div v-else>
          <h3 style="font-weight: bold">Δέν έχει δωθεί</h3>
        </div>
      </div>
      <div class="label">
        <h3>Email:</h3>
        <h3 style="font-weight: bold">{{ email }}</h3>
      </div>
      <div class="label">
        <h3>Διεύθηνση:</h3>
        <h3 style="font-weight: bold">{{ address }}</h3>
      </div>
      <div class="label">
        <h3>Τηλέφωνο:</h3>
        <h3 style="font-weight: bold">{{ phone_num }}</h3>
      </div>
      <div class="button-container" v-if="!hasApp">
        <button @click="this.router.push('/forms')"><h3>Αίτηση ΑΕΔ</h3></button>
      </div>
      <div v-else class="button-container grayed">
        <button><h3>Η Αίτηση έχει ήδη υποβληθεί</h3></button>
      </div>
    </div>
    <div class="error-msg" v-else>
      <h2>{{ errorMessage }}</h2>
    </div>
    <div class="button-container">
      <button @click="this.logout()"><h3>Έξοδος</h3></button>
    </div>
  </div>
</template>

<style scoped>
.grayed button {
  background-color: gray;
}
h2 {
  text-align: center;
  padding: 1rem 0;
}
h3 {
  padding: 0 1rem;
}
.label {
  padding: 0.7rem 0;
}
.details {
  width: 30rem;
  background-color: #f1f1f1;
  box-shadow: 0 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
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
