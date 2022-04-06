export default {
    data() {
        return {
            user: null,
        }
    },
    created() {
        // fetch on init
        this.fetchData()
    },
    methods: {
        async fetchData() {
            const url = `/user`;
            this.user = await (await fetch(url)).json();
        },
    },
    template: `
<li v-if="user == null"><a class="button" href="/oauth2/authorization/github">Login with GitHub</a> </li>
<li v-if="user != null"><li><a class="secondary" href="#/myquiz">My Quizzes</a></li>
<li v-if="user != null">{{user.name}}</li>
`
}
