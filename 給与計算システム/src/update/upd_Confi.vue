<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-sync</v-icon>
                <p>社員情報更新</p>
                <p>確認画面</p>
                <p>以下の内容で更新します。</p>
                <p>間違いがなければ確認ボタンを押してください</p>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
                <p class="text-center" :style="{'color': 'black'}">更新前社員ID : {{ empId_Before }}&nbsp;→&nbsp;&nbsp;更新後社員ID : {{ empId_After }}</p>
                <p class="text-center" :style="{'color': 'black'}">更新前社員名 : {{ empName_Before }}&nbsp;→&nbsp;&nbsp;更新後社員名 : {{ empName_After }}</p>

            </v-container>

            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="RETURN">戻る</v-btn>
                </v-col>
                <v-col cols="auto">
                    <v-spacer></v-spacer> <!-- ここに間隔を開けるスペーサー -->
                </v-col>
                <v-col cols="auto">
                    <v-btn v-on:click="CONFI">確認</v-btn>
                </v-col>
            </v-row>


        </v-main>
    </v-app>
</template>

<script>
    import axios from 'axios';
    export default {
        data() {
            return {
                errorMessage: '',
                messageVisible:false,
                empId: '', // 社員IDを保持するデータ
                empName: '', // 社員名を保持するデータ
            };
        },
        created() {
            // ルーターからパラメータを取得してデータに代入
            this.empId_Before = this.$route.params.empId_Before;
            this.empName_Before = this.$route.params.empName_Before;
            this.empId_After = this.$route.params.empId_After;
            this.empName_After = this.$route.params.empName_After;
        },
        methods: {
            RETURN() {
                this.$router.push({ name: 'upd_Inp_After', params: { empId_Before: this.empId, empName_Before: this.empName, empId_After: this.inputEmpId, empName_After: this.inputEmpName } });
            },
            CONFI() {
                axios
                    .request({
                        method: 'POST',
                        url: 'http://localhost:8080/update_Func',
                        data: {
                            idOver: this.empId_Before,
                            nameOver: this.empName_Before,
                            idOver_After: this.empId_After,
                            nameOver_After: this.empName_After
                        }
                    })
                    .then((response) => {
                        if (response.data === true) {
                            this.$router.push({ name: 'upd_Comp' });
                        }
                        else if (response.data === false) {
                            this.errorMessage = '入力された社員情報はすでに更新されています。';
                            this.$router.push({ name: 'upd_Confi' });
                        }

                    })
            },
        }
    };
</script>