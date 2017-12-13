import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import VueRouter from 'vue-router'

import App from './App.vue'
import Quiz from './Quiz.vue'
import QuizList from './QuizList.vue'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"

Vue.use(VueRouter)
Vue.use(BootstrapVue)

// navegacion
const router = new VueRouter({
    mode: 'history',
    base: __dirname,
    routes: [
        { path: '/', component: QuizList },
        { path: '/quiz/:id', component: Quiz },
    ]
})

new Vue({
    el: '#app',
    router,
    render: h => h(App)
})
