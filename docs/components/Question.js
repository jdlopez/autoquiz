export default {
    props: {
        question: Object,
        quiz: Object,
    },
    computed: {
        // computed property that auto-updates when the prop changes
        showCorrectAnswer() {
            for (let i = 0; i < this.question.answers.length; i++) {
                let a = this.question.answers[i];
                if (a.fraction === this.fractionSuccess)
                    return  a.text;
            }
            return null;
        },
        fractionSuccess() {
            return this.quiz.fractionSuccess != null ? this.quiz.fractionSuccess : 100;
        },
        fractionWrong() {
            return this.quiz.fractionWrong != null ? this.quiz.fractionWrong : 0;
        },
    },
    data() {
        return {
            answerWrong: false,
            errorMessage: null,
        }
    },
    methods: {
        testQuestion() {
            const question = this.$props.question;
            const quiz = this.$props.quiz;
            if (question.answerChosen === -1) {
                this.errorMessage = 'Choose one!';
            } else {
                this.errorMessage = null;
                for (let i = 0; i < question.answers.length; i++) {
                    question.answers[i].chosen = false;
                }
                var a = question.answers[question.answerChosen];
                a.chosen = true;
                if (a.fraction === this.fractionSuccess) {
                    this.answerWrong = false;
                } else {
                    this.answerWrong = true;
                }
            }
            question.tested = true;
            question.wrong = this.answerWrong;
        },
    },
    template: `
    <div class="fail" v-if="errorMessage != null">{{errorMessage}}</div>
    <fieldset v-if="question !== null">
        <legend>{{question.text}}</legend>
        <div v-for="(answer, index) in question.answers">
            <label>
            <input type="radio" name="answer" :value="index" v-model="question.answerChosen"  aria-invalid="false"/>
            {{answer.text}}
            </label>
            <div id="{{index}}" v-if="answer.chosen" :class="answerWrong?'fail':'hit'">            
            {{answer.feedback}}
            </div>
        </div>
        <div v-if="quiz.showCorrect && this.showCorrectAnswer != null && this.question.tested && answerWrong" class="hit">
        Correct Answer: {{showCorrectAnswer}}
        </div>
        <button @click="testQuestion" >Test</button>
    </fieldset>
`
}

