<template>
    <v-app>
        <v-main>
            <v-container>

                <v-icon>mdi-account-arrow-right</v-icon>
                <p>ログイン</p>
                <p>社員情報入力画面</p>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

                <v-text-field label="社員ID" clearable v-model="inputEmpId" @input="checkEmpId" counter="4"
                              hint=" 社員IDをA001～Z999の間の半角英数字で入力して下さい。
                              *社員IDは大文字のアルファベット＋三桁の数字の組み合わせです"
                              :rules="[id_Max_Char]"></v-text-field>
                <p v-if="idError" class="error-message">有効な社員IDを入力してください。</p>

                <v-text-field label="社員名" clearable v-model="inputEmpName" @input="checkEmpName" counter="255"
                              hint="社員名を255文字以内の全角文字で入力して下さい"
                              :rules="[name_Max_Char]"></v-text-field>
                <p v-if="nameError" class="error-message">有効な社員名を入力してください。</p>

            </v-container>

            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="LOGIN" :disabled="!inputEmpId || !inputEmpName">ログイン</v-btn>
                </v-col>
            </v-row>



        </v-main>
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
    import axios from 'axios';
    export default {
        data() {
            return {
                inputEmpId: '',
                idError: false,
                inputEmpName: '', 
                nameError: false,
                errorMessage: '',
            };
        },
        methods: {
            id_Max_Char(value) {
                if (value.length <= 4) {
                    return true;
                } else {
                    return '社員IDは4文字以下で入力してください';
                }
            },
            name_Max_Char(value) {
                if (value.length <= 255) {
                    return true; 
                } else {
                    return '社員名は255文字以下で入力してください'; 
                }
            },
            checkEmpId() {
                if (/[A-Z]{1}[0-9]{3}/.test(this.inputEmpId) && !/^(?!.*000).+$/.test(this.inputEmpId)) {
                    this.idError = true;
                } else {
                    this.idError = false;
                }
            },
            checkEmpName() {
                if (!/^[一-龯ぁ-んァ-ヶー]*$/.test(this.inputEmpName)) {
                    this.nameError = true;
                } else {
                    this.nameError = false;
                }
            },

            LOGIN() {
                axios
                    .request({
                        method: 'POST',
                        url: 'http://localhost:8080/Exist_Func',
                        data: {
                            idOver: this.inputEmpId,
                            nameOver: this.inputEmpName
                        }
                    })
                    .then((response) => {
                        if (response.data === 1) {
                            this.loggeIn = true;
                            this.$emit('loggeIn', this.loggeIn, this.inputEmpName);
                            this.$router.push({ name: 'sel', params: { loggIn: this.loggIn } });
                        }
                        else if (response.data === 0) {
                            this.errorMessage = '社員IDと社員名の組み合わせが違うか登録されていません';
                            this.$router.push({ name: 'user_Login' });
                        }

                    })
            },
            RETURN() {
                this.$router.push({ name: 'sel' });
            },
        }
    }

</script>