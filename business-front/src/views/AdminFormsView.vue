<script>
import router from '@/router'
import { useRouter } from 'vue-router'
export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      hasData: false,
      insideForm: false,
      enteredForm: '',
      formInteracted: false,
      forms: [],
      members: [],
      afmGiven: '',
      error: '',
      hasError: false
    }
  },
  methods: {
    // Retrieves Forms from backend
    async retrieveForms() {
      const token = localStorage.getItem('token')
      if (!token) {
        this.hasData = false
        return
      }
      const response = await fetch('http://localhost:8080/employee/forms', {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        this.hasError = true;
        this.error = 'Error Retrieving Forms'
        return
      }
      this.hasData = true
      const formsData = await response.json()
      this.forms = formsData
      if (!formsData.length) {
        this.hasData = false
      }
      console.log(formsData)
    },
    // formats the date to be more user readable
    formatDate(dateString) {
      const date = new Date(dateString)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      const hours = date.getHours()
      const minutes = String(date.getMinutes()).padStart(2, '0')

      return `${month}/${day}/${year} ${hours}:${minutes}`
    },
    // retrieves specific form
    async enterForm(form_id) {
      const token = localStorage.getItem('token')
      const response = await fetch(`http://localhost:8080/employee/forms/${form_id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        this.hasError = true;
        this.error = 'Error Entering Form'
        return
      }
      this.insideForm = true
      const formData = await response.json()
      this.enteredForm = formData
      if (this.enteredForm.status === 'Εγκρίθηκε') {
        this.formInteracted = true
      }
      this.members = formData.members.split(', ')
      console.log(formData)
    },
    // Sends approve to backend
    async apporveForm(form_id) {
      const token = localStorage.getItem('token')
      const response = await fetch(`http://localhost:8080/employee/accept/${form_id}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        this.hasError = true;
        this.error = 'Error Approving Form'
        return        
      }
      this.formInteracted = true
      const responseData = await response.json()
      this.afmGiven = responseData
      alert("Αίτηση Εγκρίθηκε")
      router.go(-1)
    },
    // sends reject form to backend
    async rejectForm(form_id) {
      const token = localStorage.getItem('token')
      const response = await fetch(`http://localhost:8080/employee/reject/${form_id}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        this.hasError = true;
        this.error = 'Error Approving Form'
        return
      }
      this.formInteracted = true
      const responseData = await response.json()
      console.log(responseData)
      alert("Αίτηση Απορρίφθηκε")
      router.go(-1)
    }
  },
  beforeMount() {
    this.retrieveForms()
  }
}
</script>

<template>
  <div class="container" v-if="!insideForm">
    <h2>Λίστα Αιτήσεων</h2>
    <template v-if="hasData">
      <div v-for="form in forms" :key="form.id">
        <div class="form-item">
          <div class="form-top-details">
            <h3>{{ formatDate(form.entry_date) }}</h3>
            <button @click="enterForm(form.id)"><h4>Είσοδος</h4></button>
          </div>
          <div class="form-details">
            <h3>Σκοπός Επιχείρησης</h3>
            <h3 style="font-weight: bold">{{ form.purpose }}</h3>
          </div>
          <div class="form-details">
            <h3>Ονοματεπώνυμο Εκπροσώπου</h3>
            <h3 style="font-weight: bold">{{ form.user.full_name }}</h3>
          </div>
          <div class="form-details">
            <h3>Κατάσταση</h3>
            <h3 style="font-weight: bold">{{ form.status }}</h3>
          </div>
        </div>
      </div>
    </template>
    <template v-else-if="hasError">
      <h3>{{ error }}</h3>
    </template>
    <template v-else>
      <h3>Δέν Υπάρχει κάποια Αίτηση Σε Αναμονή</h3>
    </template>
    <div class="button-container">
      <button @click="router.go(-1)"><h3>Πίσω</h3></button>
    </div>
  </div>
  <div class="container entered-form" v-else>
    <h2>Αίτηση Άσκησης Επιχειρηματικής Δραστηριότητας</h2>
    <div>
      <h3>Σκοπός Επιχείρησης:</h3>
      <h3 style="font-weight: bold">{{ enteredForm.purpose }}</h3>
    </div>
    <div>
      <h3>Καταστατικό Λειτουργίας:</h3>
      <h3 style="font-weight: bold">{{ enteredForm.katastatiko }}</h3>
    </div>
    <div>
      <h3>Έδρα Επιχείρησης:</h3>
      <h3 style="font-weight: bold">{{ enteredForm.hq }}</h3>
    </div>
    <div>
      <h3>Μέλη:</h3>
      <template v-for="(member, index) in members" :key="index">
        <h4 style="font-weight: bold">{{ member }}</h4>
      </template>
    </div>
    <div>
      <h3>Ημερομηνία Καταχώρησης:</h3>
      <h3 style="font-weight: bold">{{ formatDate(enteredForm.entry_date) }}</h3>
    </div>
    <div class="form-buttons" v-if="enteredForm.status === 'Σε Επεξεργασία'">      
      <button @click="rejectForm(enteredForm.id)"><h3>Απόριψη Αίτησης</h3></button>
      <button @click="apporveForm(enteredForm.id)"><h3>Αποδοχή Αίτησης</h3></button>
    </div>
    <div v-else>
      <div v-if="enteredForm.status === 'Απορρίφθηκε'">
        <h3>Δεν Αποδόθηκε ΑΦΜ</h3>        
      </div>
      <div v-else>
        <h3>Αποδόθηκε ΑΦΜ</h3>
        <h3 style="font-weight: bold">{{ afmGiven }}</h3>
      </div>
    </div>
    <div class="button-container">
      <button @click="insideForm = false"><h3>Πίσω</h3></button>
    </div>
  </div>
</template>

<style scoped>
.entered-form div {
  padding: 0.5rem 0;
}
h2 {
  text-align: center;
  padding: 1rem 0;
}
h3 {
  padding: 0 1rem;
}
h4 {
  padding: 0 1rem;
}
.form-item {
  padding: 0.1rem 0.25rem;
  border-top: 3px solid rgba(0, 0, 0, 0.2);
}
.form-details {
  padding: 0.3rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.form-top-details {
  display: flex;
  justify-content: space-between;
}
.form-top-details button {
  width: 25%;
  padding: 0rem 1rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}
.label {
  padding: 0.7rem 0;
}
.container {
  width: 30rem;
  background-color: #f1f1f1;
  box-shadow: 0.1rem 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
}
.button-container {
  border-top: 3px solid rgba(0, 0, 0, 0.2);
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 1rem 0;
}
.form-buttons {
  display: flex;
  justify-content: space-evenly;
}
.form-buttons button {
  width: 40%;
  padding: 0.9rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}
button {
  width: 70%;
  padding: 0.9rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}
</style>
