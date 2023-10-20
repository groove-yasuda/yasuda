<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-plus</v-icon>
                <p>社員情報登録</p>
                <p>確認画面</p>
                <p>以下の内容で登録します。</p>
                <p>間違いがなければ確認ボタンを押してください</p>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
                <p class="text-center" :style="{'color': 'black'}">社員ID : {{ empId }}</p>
                <p class="text-center" :style="{'color': 'black'}">社員名 : {{ empName }}</p>
                <p class="text-center" :style="{'color': 'black'}">生年月日 : {{ birth }}</p>
                <p class="text-center" :style="{'color': 'black'}">年齢 : {{ age }}</p>
                <p class="text-center" :style="{'color': 'black'}">性別 : {{ gender }}</p>
                <p class="text-center" :style="{'color': 'black'}">基本給 : {{ kihonkyu }}円</p>
                <p class="text-center" :style="{'color': 'black'}">交通費 : {{ koutuhi }}円</p>


            </v-container>

            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="RETURN">戻る</v-btn>
                </v-col>
                <v-col cols="auto">
                    <v-spacer></v-spacer>
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
                empId: '', 
                empName: '',
                birth: '',
                age: '',
                gender: '',
                kihonkyu: '',
                koutuhi:'',
            };
        },
        created() {

            this.empId = this.$route.params.empId;
            this.empName = this.$route.params.empName;
            this.birth = this.$route.params.birth;
            this.age = this.$route.params.age;
            this.gender = this.$route.params.gender;
            this.kihonkyu = this.$route.params.kihonkyu;
            this.koutuhi = this.$route.params.koutuhi;
        },
        methods: {
            RETURN() {
                this.$router.push({ name: 'ins_Inp' });
            },
            CONFI() {
                axios
                    .request({
                        method: 'POST',
                        url: 'http://localhost:8080/insert_Func',
                        data: {
                            idOver: this.empId,
                            nameOver: this.empName,
                            birth: this.birth,
                            age: this.age,
                            gender: this.gender,
                            kihonkyu: this.kihonkyu,
                            koutuhi: this.koutuhi,
                        }
                    })
                    .then((response) => {
                        if (response.data === true) {
                            this.$router.push({ name: 'ins_Comp' });
                        }
                        else if (response.data === false) {
                            this.errorMessage = '入力された社員IDはすでに登録されています。';
                            this.$router.push({ name: 'ins_Confi' });
                        }

                    })
            },
        }
    };
</script>