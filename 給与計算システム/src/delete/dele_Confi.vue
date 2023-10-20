<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-minus</v-icon>
                <p>社員情報登削除</p>
                <p>確認画面</p>
                <p>以下の社員情報を削除します。</p>
                <p>間違いがなければ確認ボタンを押してください</p>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
                <p class="text-center" :style="{'color': 'black'}">社員ID : {{ empId }}</p>
                <p class="text-center" :style="{'color': 'black'}">社員名 : {{ empName }}</p>

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
                birth: '', // 生年月日を保持するデータ
            };
        },
        created() {
            // ルーターからパラメータを取得してデータに代入
            this.empId = this.$route.params.empId;
            this.empName = this.$route.params.empName;
            this.birth = this.$route.params.birth;
        }, 
        methods: {
            RETURN() {
                this.$router.push({ name: 'dele_Inp' });
            },
            CONFI() {
                axios
                    .request({
                        method: 'POST',
                        url: 'http://localhost:8080/delete_Func',
                        data: {
                            idOver: this.empId,
                            nameOver: this.empName,
                            birth: this.birth,
                        }
                    })
                    .then((response) => {
                        if (response.data === true) {
                            this.$router.push({ name: 'dele_Comp' });
                        }
                        else if (response.data === false) {
                            this.errorMessage = '入力された社員情報は登録されていません。';
                            this.$router.push({ name: 'dele_Confi' });
                        }

                    })
            },
        }
    };
</script>