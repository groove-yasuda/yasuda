<template>
    <v-app id="inspire">
        <v-navigation-drawer v-model="drawer" app class="custom-drawer">
            <sideComponent v-bind:param="Name"></sideComponent>
        </v-navigation-drawer>

        <v-app-bar app>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <v-toolbar-title>社員情報管理画面</v-toolbar-title>
        </v-app-bar>

            <v-main>
                <router-view @loggeIn="get_LoggeIn" style="background-color: white"></router-view>
            </v-main>
    </v-app>
</template>

<script>
    import sideComponent from './components/sideComponent.vue'
    export default {
        name: 'App',
        components: {
            sideComponent
        },
        data() {
            return {
                drawer: null,
                loggeIn: false,
                Name: '',
                
            };
        },
        methods: {
            get_LoggeIn(loggeIn_Judge,syainName) {
                this.loggeIn = loggeIn_Judge;
                this.Name = syainName;

            },
            LOGOUT() {
                this.loggeIn = false
                this.$router.push({ name: 'user_Login' });
            },
        },
    };
</script>

<style>
    .custom-drawer {
        background-color: grey; /* 背景色をグレーに設定 */
        color: white; /* 文字色を白に設定 */
    }
</style>