<script>
import { useRouter } from 'vue-router'
export default {
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      membersList: [],
      member: '',
      form: {
        purpose: '',
        katastatiko: '',
        hq: '',
        members: '',
        entry_date: Date.now(),
        status: ''
      }
    }
  },
  methods: {
    // method to sumbit user created form to the backend
    async submitForm() {
      this.form.members = this.membersList.join(', ')
      console.log(this.form)
      const token = localStorage.getItem('token')
      const userId = parseInt(localStorage.getItem('user_id'))
      console.log(this.form)
      if (!token) {
        return
      }
      try {
        const response = await fetch(`http://localhost:8080/forms/?user_id=${userId}`, {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.form)
        })

        if (!response.ok) {
          throw new Error('Failed to submit form')
        }

        const responseData = await response.json()
        alert('Η Αίτηση Καταχωρήθηκε με Επιτυχία')
        console.log('Form submitted successfully', responseData)
        this.router.push('/user')
      } catch (error) {
        console.error('Error submitting form:', error)
      }
    },
    // add member to member list
    addMember() {
      if (!this.member) return
      this.membersList.push(this.member)
      this.member = ''
    },
    // remove member from member list
    removeMember(index) {
      this.membersList.splice(index, 1)
    }
  }
}
</script>

<template>
  <div>
    <h1>Αίτηση για Άσκηση Επιχειρηματικής Δραστηριότητας</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label>Σκοπός Επιχείρησης:</label>
        <input type="text" v-model="form.purpose" />
      </div>
      <div>
        <label>Καταστατικό Λειτουργίας:</label>
        <input type="text" v-model="form.katastatiko" />
      </div>
      <div>
        <label>Έδρα Επιχείρησης:</label>
        <input type="text" v-model="form.hq" />
      </div>
      <div>
        <label>Πρόσθεση Μέλους:</label>
        <input type="text" v-model="member" />
        <div>
          <div class="add-member">
            <button @click.prevent="addMember()">Πρόσθεσε</button>
            <button @click.prevent="removeMember()">Αφαίρεσε</button>
          </div>
        </div>
      </div>
      <div class="member-class">
        <label>Μέλη:</label>
        <template v-if="membersList.length">
          <div v-for="(member, index) in membersList" :key="index">
            {{ member }}
          </div>
        </template>
        <template v-else>
          <label>Κανένα Μέλος</label>
        </template>
      </div>
      <div>
        <button type="submit">Submit</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
div {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

form {
  width: 30rem;
  background-color: #f1f1f1;
  box-shadow: 0 0.25rem 0.5rem 0 rgba(0, 0, 0, 0.2);
  padding: 2rem;
}

h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #333;
}

input[type='text'] {
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}
.member-class {
  padding: 1rem;
}
.add-member {
  display: flex;
  flex-direction: row;
}
.add-member button {
  width: 10rem;
  margin: 0 0.25rem;
}
button {
  width: 20rem;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: #0056b3;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #004494;
}
</style>
