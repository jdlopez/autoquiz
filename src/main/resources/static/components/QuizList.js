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
            const urlQ = `/quizzes`;
            this.quizzes = await (await fetch(urlQ)).json();
            this.tags = await (await fetch('/quiz/tags')).json();
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
<h2>Tags</h2>
<ul>
<li v-for="{ name, counter } in tags">
<router-link :to="'/question/' + name" active-class="button shadowed secondary">{{name}} ({{counter}})</router-link>
</li>
</ul>
`
}

