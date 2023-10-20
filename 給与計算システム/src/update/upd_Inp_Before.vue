<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-sync</v-icon>
                <p>社員情報更新</p>
                <p>更新対象入力画面</p>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

                <v-text-field label="社員ID" clearable v-model="inputEmpId" @input="checkEmpId" counter="4"
                              :rules="[id_Max_Char]"></v-text-field>
                <p v-if="idError" class="error-message">有効な社員IDを入力してください。</p>

                <v-text-field label="社員名" clearable v-model="inputEmpName" @input="checkEmpName" counter="255"
                              :rules="[name_Max_Char]"></v-text-field>
                <p v-if="nameError" class="error-message">有効な社員名を入力してください。</p>
            </v-container>

            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="UPDATE" :disabled="nameError || idError ||
                           !inputEmpId || !inputEmpName">更新</v-btn>
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
                errorMessage:'',
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
                axios.request({
                    method: 'POST',
                    url: 'http://localhost:8080/id_Check',
                    data: {
                        idOver: this.inputEmpId,
                    }
                })
                    .then((response) => {
                        if (response.data === 1) {
                            this.errorMessage = '';
                            if (/[A-Z]{1}[0-9]{3}/.test(this.inputEmpId) && !/^(?!.*000).+$/.test(this.inputEmpId)) {
                                this.idError = true;
                            } else {
                                this.idError = false;
                            }

                        } else if (response.data === 0) {
                            this.errorMessage = '社員IDが登録されていません';
                            this.idError = false;
                        }
                    })
            },
            checkEmpName() {
                if (!/^[一-龯ぁ-んァ-ヶー]*$/.test(this.inputEmpName)) {
                    this.nameError = true;
                } else {
                    this.nameError = false;
                }
            },

            UPDATE() {
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
                            this.$router.push({ name: 'upd_Inp_After', params: { empId: this.inputEmpId, empName: this.inputEmpName } });
                        }
                        else if (response.data === 0) {
                            this.errorMessage = '社員IDと社員名の組み合わせが違います';
                            this.$router.push({ name: 'upd_Inp_Before' });
                        }

                    })
            },
        }
    }

</script>