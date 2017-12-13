<template>
    <div>

    Algo {{ $route.params.id }}
        <p>{{this.quiz.title}}</p>
    </div>
</template>

<script>
    export default {
        name: "quiz",
        data() {
            return {
                quizId: null,
                quiz: {
                    title: ''
                },
            }
        },
        computed: {
        },
        created() {
            this.fetchData()
        },
        watch: {
            // call again the method if the route changes
            '$route': 'fetchData'
        },
        methods: {
            fetchData() {
                fetch('../assets/' + this.$route.params.id + '.json').then(function (response) {
                    if (response.ok) {
                        response.blob().then(function (myBlob) {
                            this.quiz = JSON.parse(myBlob);
                        });
                    } else {
                        this.quiz = {"title": "ERROR, no se ha podido cargar"};
                        console.log('Network response was not ok.');
                    }
                })
                    .catch(function (error) {
                        console.log('There has been a problem with your fetch operation: ' + error.message);
                    });
            },
        }
    }
</script>

<style scoped>

</style>