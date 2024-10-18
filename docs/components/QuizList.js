export default {
    data() {
        return {
            quizzes: null,
            tags: null,
        }
    },
    created() {
        // fetch on init
        this.fetchData()
    },
    methods: {
        async fetchData() {
            const urlQ = `./data/quizzes.json`;
            this.quizzes = await (await fetch(urlQ)).json();
        },
    },
    template: `
<h2>This are available quizzes</h2>
<ul>
<li v-for="{ id,title,instructions } in quizzes">
<h3>{{title}} <small><i>{{instructions}}</i></small></h3> 
<router-link :to="'/quiz/' + id" active-class="button shadowed primary">Go to {{id}}</router-link>
</li>
</ul>
`
}

