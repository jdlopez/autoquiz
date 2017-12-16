<template>
    <div>

        <h2>{{quiz.title}}</h2>
        <blockquote>{{quiz.instructions}}</blockquote>
        <b-alert :show="errorMessage != null" variant="danger"><i class="fas fa-exclamation-circle"></i> {{errorMessage}}</b-alert>
        <div class="card" v-if="actualQuestion !== null">
            <div class="card-body">
                <h4 class="card-title"><span class="badge badge-secondary">{{(actualIndex+1) + '/' + quiz.questions.length}}</span>
                    {{actualQuestion.text}}
                </h4>
                <h6 class="card-subtitle mb-2 text-muted">
                    {{actualQuestion.instructions}}
                </h6>
                <p class="card-text">
                <div class="form-check" v-for="(answer, index) in actualQuestion.answers">
                    <i class="far fa-check-circle alert-success" v-if="answer.fraction === 100 && showNext && quiz.showCorrect"></i>
                    <label class="form-check-label">
                        <input class="form-check-input" type="radio" name="answer" :value="index" v-model="answerChosen" />
                        {{answer.text}}
                    </label>
                </div>
                </p>
                <b-alert :show="success != null" variant="success"><i class="far fa-check-circle"></i> {{success}}</b-alert>
                <b-alert :show="wrong != null" variant="danger"><i class="fas fa-exclamation-circle"></i> {{wrong}}</b-alert>
                <button class="btn btn-primary" @click="testQuestion" ><i class="far fa-check-circle"></i> Test</button>
                <button class="btn btn-secondary" v-if="showNext" @click="nextQuestion" ><i class="fas fa-arrow-circle-right"></i> Next</button>
            </div>
        </div>

        <b-card v-if="showResume" title="Resume">
            <b-alert show variant="success"><i class="far fa-check-circle"></i> OK: {{totalSuccess}}</b-alert>
            <b-alert show variant="danger"><i class="fas fa-exclamation-circle"></i> ERROR: {{totalWrong}}</b-alert>
            <b-alert show variant="info"> TOTAL: {{totalPercentage}} %</b-alert>
            <b-alert :show="totalPercentage > quiz.passFraction" variant="success"><i class="fas fa-exclamation"></i> PASSED!!</b-alert>

        </b-card>
    </div>
</template>

<script>
    import axios from 'axios';

    function arrayShuffle(array) {
        var i = 0
            , j = 0
            , temp = null;

        for (i = array.length - 1; i > 0; i -= 1) {
            j = Math.floor(Math.random() * (i + 1));
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    export default {
        name: "quiz",
        data() {
            return {
                quizId: null,
                quiz: {
                    title: 'Loading...',
                    instructions: null,
                    showCorrect: false,
                    questions: [],
                },
                actualQuestion: null,
                actualIndex: 0,
                showNext: false,
                answerChosen: -1,
                wrong: null,
                success: null,
                showResume: false,
                totalSuccess: 0,
                totalWrong: 0,
                errorMessage: null,
            }
        },
        computed: {
            totalPercentage() {
                // 2 decimals only
                return Math.round(this.totalSuccess / this.quiz.questions.length * 100 * 100) / 100;
            },
        },
        created() {
            this.fetchData()
        },
        watch: {
            // call again the method if the route changes
            '$route': 'fetchData'
        },
        methods: {
            nextQuestion() {
                if ((this.actualIndex+1) === this.quiz.questions.length) {
                    this.showResume = true;
                    return;
                }
                this.actualIndex++;
                this.wrong = null;
                this.success = null;
                this.answerChosen = -1;
                this.actualQuestion = this.quiz.questions[this.actualIndex];
                this.showNext = false;
            },
            testQuestion() {
                if (this.answerChosen === -1) {
                    this.wrong = 'Choose one!';
                } else {
                    var a = this.actualQuestion.answers[this.answerChosen];
                    a.chosen = true;
                    if (a.fraction === 100) {
                        this.success = a.feedback;
                        this.wrong = null;
                        this.totalSuccess++;
                    } else {
                        this.wrong = a.feedback;
                        this.success = null;
                        this.totalWrong++;
                    }
                    this.showNext = true;
                }
            },
            fetchData() {
                const url = __API__ + this.$route.params.id ; // '../' + this.$route.params.id + '.quiz';
                axios.get(url)
                    .then(response => {
                        // JSON responses are automatically parsed.
                        this.quiz = response.data;
                        if (this.quiz.questions.length > 0)
                            arrayShuffle(this.quiz.questions);
                            // this.quiz.questions.forEach(function(q) {
                            //     suffle(q.answers);
                            // });
                            this.actualIndex = 0;
                            this.actualQuestion = this.quiz.questions[this.actualIndex];
                    })
                    .catch(e => {
                        this.errorMessage = "ERROR loading quiz. " + e
                    });

            },
        }
    }
</script>

<style scoped>

</style>