export default {
    props: {
        question: Object,
    },
    data() {
        return {
            answerWrong: false,
            answerChosen: -1,
            errorMessage: null,
        }
    },
    methods: {
        testQuestion() {
            const question = this.$props.question;
            if (this.answerChosen === -1) {
                this.errorMessage = 'Choose one!';
            } else {
                this.errorMessage = null;
                for (let i = 0; i < question.answers.length; i++) {
                    question.answers[i].chosen = false;
                }
                var a = question.answers[this.answerChosen];
                a.chosen = true;
                if (a.fraction === 100) {
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
            <input type="radio" name="answer" :value="index" v-model="answerChosen"  aria-invalid="false"/>
            {{answer.text}}
            </label>
            <div id="{{index}}" v-if="answer.chosen" :class="answerWrong?'fail':'hit'">            
            {{answer.feedback}}
            </div>
        </div>
        <button @click="testQuestion" >Test</button>
    </fieldset>
`
}

