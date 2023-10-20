<template>
    <v-app>
        <v-main>

            <v-container>
                <v-icon>mdi-account-minus</v-icon>
                <p>社員情報削除</p>
                <p>削除対象入力画面</p>


                    <v-text-field label="社員ID" clearable v-model="inputEmpId" @input="checkEmpId" counter="4"
                                  hint=" 社員IDをA001～Z999の間の半角英数字で入力して下さい。
                              *社員IDは大文字のアルファベット＋三桁の数字の組み合わせです"
                                  :rules="[id_Max_Char]" class="custom-hint-style"></v-text-field>
                    <p v-if="idError" class="error-message">有効な社員IDを入力してください。</p>

                    <v-text-field label="社員名" clearable v-model="inputEmpName" @input="checkEmpName" counter="255"
                                  hint="社員名を255文字以内の全角文字で入力して下さい"
                                  :rules="[name_Max_Char]" class="custom-hint-style"></v-text-field>
                    <p v-if="nameError" class="error-message">有効な社員名を入力してください。</p>

            </v-container>
            <br>
            <v-row justify="center">
                <div>
                    <label for="year">生年月日:</label>
                    <select v-model="selectedYear" id="year" class="select-dropdown">
                        <option v-for="year in yearRange" :value="year" :key="year">{{ year }}</option>
                    </select>年

                    <label for="month"></label>
                    <select v-model="selectedMonth" id="month" class="select-dropdown">
                        <option v-for="month in months" :value="month" :key="month">{{ month }}</option>
                    </select>月

                    <label for="day"></label>
                    <select v-model="selectedDay" id="day" class="select-dropdown">
                        <option v-for="day in dayRange" :value="day" :key="day">{{ day }}</option>
                    </select>日
                </div>
            </v-row>
            <br><br>

                <v-row justify="center">
                    <v-col cols="auto">
                        <v-btn v-on:click="DELETE" :disabled="nameError || idError ||
                           !inputEmpId || !inputEmpName">削除</v-btn>
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
    .custom-hint-style .v-messages__message {
        font-size: 11px; /* ヒントの文字サイズを設定 */
        color: #333;
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
                years: [1990], // 生年月日の年の選択肢
                months: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12], // 生年月日の月の選択肢
                days: [1], // 生年月日の日の選択肢
                selectedYear: 2000, // 選択された年
                selectedMonth: 1, // 選択された月
                selectedDay: 1, // 選択された日
                birth:'',
            };
        },
        computed: {
            yearRange() {
                const currentYear = new Date().getFullYear();
                const startYear = currentYear - 100; // 100年分の範囲を生成
                return Array.from({ length: 101 }, (_, i) => startYear + i);
            },
            dayRange() {
                return Array.from({ length: 31 }, (_, i) => i + 1); // 日の選択肢を1から31まで生成
            },
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
                            this.errorMessage = true;
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

            DELETE() {
                this.birth = this.selectedYear + '/' + this.selectedMonth + '/' + this.selectedDay;
                this.$router.push({ name: 'dele_Confi', params: { empId: this.inputEmpId, empName: this.inputEmpName,birth: this.birth } });
            },
        }
    }

</script>