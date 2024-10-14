<script>
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      hasData: false,
      users: [],
      insideUser: false,
      username: '',
      password: '',
      full_name: '',
      email: '',
      address: '',
      phone_num: ''
    }
  },
  methods: {
    // method to retrieve all users from backend
    async retrieveUsers() {
      const token = localStorage.getItem('token')
      if (!token) {
        this.hasData = false
        return
      }
      const response = await fetch('http://localhost:8080/admin/users/all', {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        console.warn('Retrieve Users Error')
      }
      const usersData = await response.json()
      console.log(usersData)
      this.users = usersData
      if (usersData.length > 0) {
        this.hasData = true
        this.insideUser = false
      }
    },
    // method to delete user based on username from backend
    async deleteUser(username) {
      const token = localStorage.getItem('token')
      if (!token) {
        this.hasData = false
        return
      }
      const response = await fetch(`http://localhost:8080/admin/delete/${username}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (!response.ok) {
        console.log('Error')
      }
      alert('Χρήστης Διαγράφθηκε')
      this.router.go(-1)
    },
    // methrod to retrieve single user
    async getSingleUser(username) {
      this.insideUser = true
      const token = localStorage.getItem('token')
      if (!token) {
        this.hasData = false
        return
      }
      const response = await fetch(`http://localhost:8080/user/${username}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      if (response.ok) {
        this.hasData = true
      }
      const userData = await response.json()
      console.warn(userData)
      this.username = username
      this.full_name = userData.full_name
      this.email = userData.email
      this.address = userData.address
      this.phone_num = userData.phone_num
    },
    // method to update specific user on the database based on username
    async updateUser(username) {
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.hasData = false
          return
        }
        const response = await fetch(`http://localhost:8080/admin/update/${username}`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            full_name: this.full_name,
            email: this.email,
            address: this.address,
            phone_num: this.phone_num
          })
        })

        if (!response.ok) {
          throw new Error('Login failed')
        }

        const data = await response.json()
        console.log(data)
        alert('Οι αλλαγές αποθηκεύτικαν με επυτιχία')
      } catch (error) {
        this.error = true
        this.errorMessage = error.message || 'Registration failed'
      }
    }
  },
  beforeMount() {
    this.retrieveUsers()
  }
}
</script>

<template>
  <div class="container" v-if="!insideUser">
    <h2>Λίστα Χρηστών</h2>
    <template v-if="hasData">
      <div v-for="user in users" :key="user.id" class="user-item">
        <div class="user-details">
          <h3>Όνομα Χρήστη:</h3>
          <h3 style="font-weight: bold">{{ user.username }}</h3>
        </div>
        <div class="user-details">
          <h3>Ονοματεπώνυμο:</h3>
          <h3 style="font-weight: bold">{{ user.full_name }}</h3>
        </div>
        <div v-if="user.authorities[0].authority === 'USER'" class="user-details">
          <h3>ΑΦΜ:</h3>
          <h3 v-if="user.afm" style="font-weight: bold">{{ user.afm }}</h3>
          <h3 v-else style="font-weight: bold">Δέν Έχει δωθεί</h3>
        </div>
        <div class="user-details">
          <h3>Ρόλος:</h3>
          <h3 style="font-weight: bold">{{ user.authorities[0].authority }}</h3>
        </div>
        <div class="modify-buttons">          
          <button @click="deleteUser(user.username)"><h3>Διαγραφή</h3></button>
          <button v-if="user.authorities[0].authority === 'USER'" @click="getSingleUser(user.username)"><h3>Τροποποίηση</h3></button>
        </div>
      </div>
    </template>
    <template v-else>
      <h3>Δεν υπάρχουν χρήστες για εμφάνιση.</h3>
    </template>
    <div class="button-container">
      <button @click="router.go(-1)"><h3>Πίσω</h3></button>
    </div>
  </div>
  <div class="form-container" v-else>
    <h2>Επεξεργασία Χρήστη</h2>    
    <div class="form-group">
      <label for="full-name">Ονοματεπώνυμο:</label>
      <input type="text" id="full-name" v-model="full_name" />
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="text" id="email" v-model="email" />
    </div>
    <div class="form-group">
      <label for="address">Διεύθηνση:</label>
      <input type="text" id="address" v-model="address" />
    </div>
    <div class="form-group">
      <label for="phone-num">Τηλέφωνο:</label>
      <input type="text" id="phone-num" v-model="phone_num" />
    </div>
    <div class="button-container">
      <button @click="updateUser(this.username)"><h3>Ανανέωση Στοιχείων</h3></button>
    </div>
    <div class="button-container">
      <button @click="retrieveUsers()"><h3>Πίσω</h3></button>
    </div>
  </div>
</template>

<style scoped>
.modify-buttons {
  display: flex;
  justify-content: space-evenly;
}
.modify-buttons button {
  width: 30%;
  padding: 0.4rem 0.25rem;
  margin-bottom: 0.25rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}
.user-item {
  padding: 0.1rem 0.25rem;
  border-top: 3px solid rgba(0, 0, 0, 0.2);
}
.user-details {
  padding: 0.3rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
.label {
  padding: 0.7rem 0;
}
.container {
  width: 30rem;
  background-color: #f1f1f1;
  box-shadow: 0.1rem 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
}
.button-container {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 0.525rem 0;
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
input[type='text'],
input[type='password'] {
  width: 100%;
  padding: 0.5rem;
  border: 0.0625rem solid #ddd;
  border-radius: 0.25rem;
}
.form-container {
  position: relative;
  max-width: 25rem;
  margin: 3.125rem auto;
  padding: 3rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.3125rem;
  background-color: #f9f9f9;
  box-shadow: 0 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
}
</style>
