<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-minus</v-icon>
                <p>社員情報削除</p>
                <p>完了画面</p>
                <p>社員情報の削除が完了しました。</p>


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
               this.$router.push({ name: 'dele_Inp' });
            }
        }
    }

</script>