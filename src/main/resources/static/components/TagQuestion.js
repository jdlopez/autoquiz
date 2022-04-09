import Question from './Question.js'

export default {
    props: {
        tag: null, // not working??
    },
    components: {
        Question,
    },
    data() {
        return {
            tagName: null,
            question: null
        }
    },
    created() {
        // fetch on init
        this.fetchData()
    },
    methods: {
        async fetchData() {
            this.tagName = this.$route.params.tag;
            const url = `/question/tag/` + this.tagName;
            this.question = await (await fetch(url)).json();
        },
        anotherQuestion() {
            this.fetchData()
        }
    },
    template: `
<h2>Random question for {{tagName}}</h2>
<!-- todo: add current ok/ko counters -->
<Question :question='question' ></Question>
<button @click="anotherQuestion" >Next</button>
  `,
}