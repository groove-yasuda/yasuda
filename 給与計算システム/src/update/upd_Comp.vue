<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-sync</v-icon>
                <p>社員情報更新</p>
                <p>完了画面</p>
                <p>社員情報の更新が完了しました。</p>


            </v-container>
            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="RET_INP">戻る</v-btn>
                </v-col>
            </v-row>


        </v-main>
    </v-app>
</template>


<style type="text/css">
    p {
        font-family: 'Times New Roman', Times, serif;
    }
</style>

<script>
    export default {
        data() {
            return {
                inputEmpId: '', // 社員IDのデータ
                inputEmpName: '', // 社員名のデータ
            };
        },
        methods: {
            RET_INP() {
               this.$router.push({ name: 'upd_Inp_Before' });
            }
        }
    }

</script>