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
      error: false,
      errorMessage: ''
    }
  },
  methods: {
    clearForm() {
      this.username = ''
      this.password = ''
    },
    // sends request to login the user
    async login() {
      try {
        const response = await fetch('http://localhost:8080/auth/login', {
          method: 'POST',
          headers: {
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
        if (data && data.user) {
          console.log(data)
          localStorage.setItem('username', data.user.username)
          localStorage.setItem('user_id', data.user.id)
          localStorage.setItem('token', data.jwt)
          if (data.user.authorities[0].authority === 'USER') {
            localStorage.setItem('userRole', 'user')
            this.router.push('/user')
          } else if (data.user.authorities[0].authority === 'ADMIN'){
            localStorage.setItem('userRole', 'admin')
            this.router.push('/admin')
          } else {
            localStorage.setItem('userRole','employee')
            console.log('employee + ')
            this.router.push('/employee')
          }
        } else {
          this.error = true
          this.errorMessage = 'Wrong Credentials'
        }
      } catch (error) {
        this.error = true
        this.clearForm()
        this.errorMessage = error.message || 'Login failed'
      }
    }
  }
}
</script>

<template>
  <div class="login-container">
    <h1>Δημόσια Οικονομική Υπηρεσία</h1>
    <form @submit.prevent="login">
      <div v-if="error" class="error-message">
        {{ errorMessage }}
      </div>
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<style scoped>
.login-container {
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
