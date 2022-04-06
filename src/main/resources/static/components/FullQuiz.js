import Question from './Question.js'

function shuffle(array) {
    let currentIndex = array.length,  randomIndex;

    while (currentIndex != 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;
        [array[currentIndex], array[randomIndex]] = [
            array[randomIndex], array[currentIndex]];
    }

    return array;
}
export default {
    components: {
        Question,
    },
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
            actualIndex: 0,
            actualQuestion: null,
            showFinish: false,
            showNextButton: false,
            totalSuccess: 0,
            totalWrong: 0,
        }
    },
    computed: {
        totalPercentage() {
            // 2 decimals only
            return Math.round(this.actualIndex / this.quiz.questions.length * 100 * 100) / 100;
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
        endQuiz() {
            this.showNextButton = false;
            this.showFinish = true;
        },
        nextQuestion() {
            if (this.actualIndex+1 == this.quiz.questions.length) {
                this.endQuiz();
            } else {
                if (this.actualQuestion.wrong === true)
                    this.totalWrong++;
                else
                    this.totalSuccess++;
                this.actualIndex++;
                this.actualQuestion = this.quiz.questions[this.actualIndex];
                this.showNextButton = false;
            }

        },
        async fetchData() {
            const url = `/quiz/` + this.$route.params.id ;
            this.quiz = await (await fetch(url)).json();
            if (this.quiz.suffleQuestions) {
                this.quiz.questions = shuffle(this.quiz.questions);
            }
            this.actualIndex = 0;
            this.actualQuestion = this.quiz.questions[this.actualIndex];
        },
    },
    template: `
    <div>
        <header>{{quiz.title}}</header>
        <p >{{quiz.instructions}}</p>        
    </div>
    <article v-if="showFinish">
        <h3 class="doc">Congratulations you have reached the end of the quiz</h3>
        <ul>
        <li>total Success: {{totalSuccess}}</li>
        <li>total Wrong: {{totalWrong}}</li>
        <li>Percentage: {{totalPercentage}}</li>
        </ul>
    </article>

    <div>
    {{actualIndex+1}}/{{quiz.questions.length}}
    <progress :value="actualIndex+1" :max="quiz.questions.length"></progress>
    </div>
    <Question :question="actualQuestion"></Question>
    <button class="secondary" v-if="actualQuestion != null && actualQuestion.tested" @click="nextQuestion">&rArr; Next</button>
    <hr/>        
    <a href="javascript:" role="button" class="secondary outline" @click="endQuiz">End Quiz</a>
`
}

