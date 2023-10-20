<template>
    
    <v-app>
        <br><br><br><br><br>
        <v-row justify="center">
            <v-col cols="auto">
                <v-btn v-on:click="INSERT">登録</v-btn>
                <v-btn v-on:click="UPDATE">更新</v-btn>
                <v-btn v-on:click="DELETE">削除</v-btn>
                <v-btn v-on:click="SEARCH">検索</v-btn>
            </v-col>
        </v-row>

        <v-row justify="center">
            <v-col cols="auto">
                <v-btn v-on:click="LOGOUT">ログアウト</v-btn>
            </v-col>
        </v-row>

    </v-app>
</template>


<style type="text/css">
    p {
        font-family: 'Century', Times, serif;
        color:black;
    }
    .error-message {
        color: red;
    }
</style>

<script>
    export default {
        data() {
            return {
                inputEmpId: '',
                idError: false,
                inputEmpName: '', 
                nameError: false,
            };
        },
        methods: {
            INSERT() {
               this.$router.push({ name: 'ins_Inp' });
            },
            UPDATE() {
                this.$router.push({ name: 'upd_Inp_Before' });
            },
            DELETE() {
                this.$router.push({ name: 'dele_Inp' });
            },
            SEARCH() {
                this.$router.push({ name: 'sele_Inp' });
            },
            LOGOUT() {
                this.loggeIn = false
                this.$router.push({ name: 'user_Login' });
            },

        }
    }

</script>