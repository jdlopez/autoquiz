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
            <b-list-group-item v-for="q in questionsFiltered" :key="q.id"
            ><router-link :to="'/quiz/quiz/'+q.id">{{q.title}} </router-link> </b-list-group-item>
            <!-- <i class="far fa-clipboard"></i> copy to clipboard -->
        </b-list-group>

    </b-container>

</template>

<script>
    import axios from 'axios';

    export default {
        name: "quiz-list",
        data() {
            return {
                search: "",
                questions: [],
                errorMessage: null,
            }
        },
        created(){
            const url = __API__ + 'quiz_list.json';
            axios.get(url)
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.questions = response.data;
                })
                .catch(e => {
                    this.errorMessage = "ERROR loading quiz. " + e;
                });

        },
        computed: {
            questionsFiltered() {
                if (this.search.length > 1) {
                    return this.questions.filter(function (elem) {
                        return (elem.title.toLowerCase().indexOf(this.search.toLowerCase()) > -1);
                    }, this);
                } else {
                    return this.questions;
                }
            },
        }
    }
</script>

<style scoped>

</style>