<template>
    <b-container>
        <b-jumbotron header="Quiz viewer"
                     lead="Visor de cuestionarios"
        >
            <p>Visor simple de cuestionarios con formato moodle</p>
            <!--
          <b-btn variant="primary" href="https://bootstrap-vue.js.org/">More Info</b-btn>
          -->
        </b-jumbotron>

        <b-form-input v-model.trim="search" placeholder="Texto a filtrar"></b-form-input>
        <b-list-group>
            <b-list-group-item v-for="q in questions"><router-link :to="'/quiz/'+q.id">{{q.title}} <i class="far fa-clipboard"></i></router-link> </b-list-group-item>
        </b-list-group>

        <!--
      <b-alert variant="success" :show="showAlert">
        Hello {{ name }}
      </b-alert>
      -->
    </b-container>

</template>

<script>
    export default {
        name: "quiz-list",
        data() {
            return {
                search: '',
                questions: [
                    {
                        "id" : "cce2a1c2-4f8f-4eb3-ba2c-47d5d0c7a3ff",
                        "title" : "E.M.T ( Test 1)"
                    },
                    {
                        "id" : "bbbb",
                        "title" : "E.M.T ( Test 2)"
                    },
                ],
            }
        },
        computed: {
            showAlert() {
                return this.name.length > 4 ? true : false;
            }
        }
    }
</script>

<style scoped>

</style>