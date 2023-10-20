import Vue from 'vue'
import VueRouter from 'vue-router'
import Sel from './components/sel_Func.vue'
import ins_Inp from './insert/ins_Inp.vue'
import ins_Confi from './insert/ins_Confi.vue'
import ins_Comp from './insert/ins_Comp.vue'
import upd_Inp_Before from './update/upd_Inp_Before.vue'
import upd_Inp_After from './update/upd_Inp_After.vue'
import upd_Confi from './update/upd_Confi.vue'
import upd_Comp from './update/upd_Comp.vue'
import dele_Inp from './delete/dele_Inp.vue'
import dele_Confi from './delete/dele_Confi.vue'
import dele_Comp from './delete/dele_Comp.vue'
import sele_Inp from './select/sele_Inp.vue'
import sele_Result from './select/sele_Result.vue'
import sele_Detail from './select/sele_Detail.vue'
import user_Login from './login/user_Login.vue'
import test_Test from './login/test_Test.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'user_Login',
        component: user_Login
    },
    {
        path: '/sel',
        name: 'sel',
        component: Sel
    },
    {
        path: '/ins_inp',
        name: 'ins_Inp',
        component: ins_Inp
    },
    {
        path: '/ins_confi',
        name: 'ins_Confi',
        component: ins_Confi
    },
    {
        path: '/ins_comp',
        name: 'ins_Comp',
        component: ins_Comp
    },
    {
        path: '/upd_inp_before',
        name: 'upd_Inp_Before',
        component: upd_Inp_Before
    },
    {
        path: '/upd_inp_after',
        name: 'upd_Inp_After',
        component: upd_Inp_After
    },
    {
        path: '/upd_confi',
        name: 'upd_Confi',
        component: upd_Confi
    },
    {
        path: '/upd_comp',
        name: 'upd_Comp',
        component: upd_Comp
    },
    {
        path: '/dele_inp',
        name: 'dele_Inp',
        component: dele_Inp
    },
    {
        path: '/dele_confi',
        name: 'dele_Confi',
        component: dele_Confi
    },
    {
        path: '/dele_comp',
        name: 'dele_Comp',
        component: dele_Comp
    },
    {
        path: '/sele_inp',
        name: 'sele_Inp',
        component: sele_Inp
    },
    {
        path: '/sele_detail',
        name: 'sele_Detail',
        component: sele_Detail
    },
    {
        path: '/sele_result',
        name: 'sele_Result',
        component: sele_Result
    },
    {
        path: '/test_test',
        name: 'test_Test',
        component: test_Test
    },
]

const router = new VueRouter({
    routes,
})

export default router;