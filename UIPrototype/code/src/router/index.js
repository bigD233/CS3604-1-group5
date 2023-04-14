import { createRouter, createWebHashHistory } from "vue-router";
import Index from '@/views/Index.vue';
import Home from '@/views/Home.vue';
import Start from '@/views/Start.vue';
import Login from '@/views/Login.vue';
import Signup from '@/views/Signup.vue';
import CaseHome from '@/views/CaseHome.vue';
import CaseSearch from '@/views/CaseSearch.vue';
import Case from '@/views/Case.vue';
import QueSearch from '@/views/QueSearch.vue';
import Question from '@/views/Question.vue';
import QAHome from '@/views/QAHome.vue';
import User from '@/views/User.vue';
import CasePublish from '@/views/CasePublish.vue';
import QuestionEdit from '@/views/QuestionEdit.vue';
import AnswerEdit from '@/views/AnswerEdit.vue';
import UserSearch from '@/views/UserSearch.vue';
import NoContent from '@/views/NoContent.vue';

const routes = [
    {
        path: '/',
        redirect: { name: 'start' },
    },
    {
        path: '/index',
        component: Index,
    },
    {
        path: '/start',
        name: 'start',
        component: Start,
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
    {
        path: '/signup',
        name: 'signup',
        component: Signup,
    },
    {
        path: '/home',
        name: 'home',
        component: Home,
    },
    {
        path: '/casehome',
        component: CaseHome,
    },
    {
        path: '/casesearch',
        component: CaseSearch,
    },
    {
        path: '/case/:caseId/:visitorId',
        name:'Case',
        component: Case,
    },
    {
        path: '/quesearch',
        component: QueSearch,
    },
    {
        path: '/question/:questionId/:answerId/:visitorId',
        name: 'Question',
        component: Question,
    },
    {
        path: '/qahome',
        component: QAHome,
    },
    {
        path: '/user',
        component: User,
    },
    {
        path: '/user/:userId',
        component: User,
    },
    {
        path:'/casepublish/:publishId',
        name:'Casepublish',
        component: CasePublish,
    },
    {
        path:'/question/edit/:markdown/:questionId',
        component: QuestionEdit,
    },
    {
        path:'/answer/edit/:markdown/:questionId/:answerId/',
        component: AnswerEdit,
    },
    {
        path:'/usersearch',
        component: UserSearch,
    },
    {
        path:'/nocontent',
        component: NoContent,
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;
