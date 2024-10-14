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
      password: '',
      full_name: '',
      email: '',
      address: '',
      phone_num: '',
      error: false,
      errorMessage: '',
      role: 'user'
    }
  },
  methods: {
    async registerEmployee() {
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.hasData = false
          return
        }
        const response = await fetch('http://localhost:8080/admin/register-employee', {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
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
        alert("Εγγραφή με επιτυχία")
      } catch (error) {
        this.error = true
        this.errorMessage = error.message || 'Registration failed'
      }
    },
    // method to register a new user
    async registerUser() {
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.hasData = false
          return
        }
        const response = await fetch('http://localhost:8080/admin/register', {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
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
        alert("Εγγραφή με επιτυχία")
      } catch (error) {
        this.error = true
        this.errorMessage = error.message || 'Registration failed'
      }
    },
    // method to register an admin user
    async registerAdmin() {
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.hasData = false
          return
        }
        const response = await fetch('http://localhost:8080/admin/register-admin', {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password
          })
        })

        if (!response.ok) {
          throw new Error('Login failed')
        }

        const data = await response.json()
        console.log(data)
      } catch (error) {
        this.error = true
        this.errorMessage = error.message || 'Registration failed'
      }
    }
  }
}
</script>

<template>
  <div class="registration-container">
    <h1>Εγγραφή Χρήστη</h1>
    <select v-model="role" class="role-select">
      <option value="employee">Employee</option>
      <option value="user">User</option>
      <option value="admin">Admin</option>
    </select>
    <form v-if="role === 'employee'" @submit.prevent="registerEmployee">
      <div v-if="error" class="error-message">
        {{ errorMessage }}
      </div>
      <div class="form-group">
        <label for="reg-username">Username:</label>
        <input type="text" id="reg-username" v-model="username" />
      </div>
      <div class="form-group">
        <label for="reg-password">Password:</label>
        <input type="password" id="reg-password" v-model="password" />
      </div>      
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
      <button type="submit" @click="router.go(-1)">Εγγραφή</button>
      <div></div>
      <button @click.prevent="router.go(-1)">Πίσω</button>
    </form>
    <form v-if="role === 'user'" @submit.prevent="registerUser">
      <div v-if="error" class="error-message">
        {{ errorMessage }}
      </div>
      <div class="form-group">
        <label for="reg-username">Username:</label>
        <input type="text" id="reg-username" v-model="username" />
      </div>
      <div class="form-group">
        <label for="reg-password">Password:</label>
        <input type="password" id="reg-password" v-model="password" />
      </div>      
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
      <button type="submit" @click="router.go(-1)">Εγγραφή</button>
      <div></div>
      <button @click.prevent="router.go(-1)">Πίσω</button>
    </form>

    <form v-if="role === 'admin'" @submit.prevent="registerAdmin">
      <div v-if="error" class="error-message">
        {{ errorMessage }}
      </div>
      <div class="form-group">
        <label for="reg-username">Username:</label>
        <input type="text" id="reg-username" v-model="username" />
      </div>
      <div class="form-group">
        <label for="reg-password">Password:</label>
        <input type="password" id="reg-password" v-model="password" />
      </div>
      <button type="submit" @click="router.go(-1)">Εγγραφή</button>
      <div></div>
      <button @click.prevent="router.go(-1)">Πίσω</button>
    </form>
  </div>
</template>

<style scoped>
.role-select {
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ddd;
  border-radius: 0.25rem;
}
.registration-container {
  position: relative;
  max-width: 25rem;
  margin: 3.125rem auto;
  padding: 3rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.3125rem;
  background-color: #f9f9f9;
  box-shadow: 0 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
}
h1 {
  text-align: center;
  color: #333;
  padding-bottom: 3rem;
}

.form-group {
  margin-bottom: 0.9375rem;
}

label {
  display: block;
  margin-bottom: 0.3125rem;
}

input[type='text'],
input[type='password'] {
  width: 100%;
  padding: 0.5rem;
  border: 0.0625rem solid #ddd;
  border-radius: 0.25rem;
}

button {
  width: 100%;
  padding: 0.625rem;
  margin-top: 0.25rem;
  border: none;
  border-radius: 0.25rem;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #004494;
}
</style>
