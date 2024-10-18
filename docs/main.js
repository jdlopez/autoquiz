import App from './components/App.js';

import Quizlist from './components/QuizList.js'
import FullQuiz from './components/FullQuiz.js'

const routes = [
    { path: '/', component: Quizlist },
    { path: '/quiz/:id', component: FullQuiz },
]
const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes, // short for `routes: routes`
})

const app = Vue.createApp({
    render: () => Vue.h(App),
});

app.use(router)

app.mount('#app');
