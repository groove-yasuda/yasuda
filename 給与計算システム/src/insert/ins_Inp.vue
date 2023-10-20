<template>
    <v-app>
        <v-main>
            <v-container>

                <v-icon>mdi-account-plus</v-icon>
                <p>社員情報登録</p>
                <p>社員情報入力画面</p>


                <v-text-field label="社員ID" clearable v-model="inputEmpId" @input="checkEmpId" counter="4"
                              hint=" 社員IDをA001～Z999の間の半角英数字で入力して下さい。
                              *社員IDは大文字のアルファベット＋三桁の数字の組み合わせです"
                              :rules="[id_Max_Char]" class="custom-hint-style"></v-text-field>
                <p v-if="idError && inputEmpId" class="error-message">有効な社員IDを入力してください。</p>

                <v-text-field label="社員名" clearable v-model="inputEmpName" @input="checkEmpName" counter="255"
                              hint="社員名を255文字以内の全角文字で入力して下さい"
                              :rules="[name_Max_Char]" class="custom-hint-style"></v-text-field>
                <p v-if="nameError" class="error-message">有効な社員名を入力してください。</p>


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
                    &nbsp;&nbsp;&nbsp;
                    <div>
                        <label for="age">年齢:</label>
                        <select v-model="selectedAge" id="age" class="select-dropdown">
                            <option v-for="age in ageRange" :value="age" :key="age">{{ age }} 歳</option>
                        </select>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    <div>
                        <label for="age">性別:</label>
                        <select v-model="selectedGender" id="gender" class="select-dropdown">
                            <option v-for="gender in genderOption" :value="gender" :key="gender">{{ gender }}</option>
                        </select>
                    </div>
                </v-row>
                <br><br>

                <v-text-field label="基本給" clearable v-model="inputKihonkyu" @input="checkKihonkyu"
                              hint=" 半角数字で入力して下さい"
                              class="custom-hint-style"></v-text-field>
                <p v-if="kihonkyuError" class="error-message">有効な値を入力してください。</p>

                <v-text-field label="交通費" clearable v-model="inputKoutuhi" @input="checkKoutuhi"
                              hint="半角数字で入力して下さい"
                              class="custom-hint-style"></v-text-field>
                <p v-if="koutuhiError" class="error-message">有効な値を入力してください。</p>
                <br><br>


                <v-row justify="center">
                    <v-col cols="auto">
                        <v-btn v-on:click="INSERT" :disabled="!inputEmpId || !inputEmpName || !inputKihonkyu || !inputKoutuhi || !idError == false || !nameError == false">登録</v-btn>
                    </v-col>
                </v-row>


            </v-container>


                                  
        </v-main>
    </v-app>
</template>


<style type="text/css">
    p {
        color:black;
    }
    .error-message {
        color: red;
    }
    .select-dropdown {
        padding: 5px; /* パディングを追加して選択肢が見やすくなります */
        border: 1px solid #ccc; /* ボーダーを追加してプルダウンの境界を明示的に表示します */
        border-radius: 4px; /* 角を丸くすることで視覚的に魅力的になります */
        background-color: white; /* 背景色を白に設定します */
        font-size: 18px; /* フォントサイズを調整できます */
        width: 65px; /* 幅を調整できます */
    }
    .custom-hint-style .v-messages__message {
        font-size: 11px; /* ヒントの文字サイズを設定 */
        color: #333;
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
                inputKihonkyu: '',
                kihonkyuError: false,
                inputKoutuhi: '',
                koutuhiError: false,
                years: [1990], // 生年月日の年の選択肢
                months: [1,2,3,4,5,6,7,8,9,10,11,12], // 生年月日の月の選択肢
                days: [1], // 生年月日の日の選択肢
                selectedYear: 2000, // 選択された年
                selectedMonth: 1, // 選択された月
                selectedDay: 1, // 選択された日
                selectedAge: 18,//選択された年齢
                selectedGender: '男性',
                genderOption: ['男性', '女性'],
            };
        },
        computed: {
            yearRange() {
                const currentYear = new Date().getFullYear();
                const startYear = currentYear - 100; // 100年分の範囲を生成
                return Array.from({ length: 101 }, (_, i) => startYear + i);
            },
            ageRange() {
                // 年齢の範囲を設定します
                const minAge = 1;
                const maxAge = 100;

                // 年齢の範囲から選択肢の配列を生成します
                const range = [];
                for (let age = minAge; age <= maxAge; age++) {
                    range.push(age);
                }

                return range;
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
                    return '社員IDは4文字で入力してください';
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
                if (!/^[一-龯ぁ-んァ-ヶー]*$/.test(this.inputEmpId)) {
                    if (!/[A-Z]{1}[0-9]{3}/.test(this.inputEmpId) || !/^(?!.*000).+$/.test(this.inputEmpId)) {
                        this.idError = true;
                    } else {
                        this.idError = false;
                    }
                } else {
                this.idError = true;
            }
            },
            checkEmpName() {
                if (!/^[一-龯ぁ-んァ-ヶー]*$/.test(this.inputEmpName)) {
                    this.nameError = true;
                } else {
                    this.nameError = false;
                }
            },
            checkKihonkyu() {
                if (!/^[0-9]*$/.test(this.inputKihonkyu)) {
                    this.kihonkyuError = true;
                } else {
                    this.kihonkyuError = false;
                }
            },
            checkKoutuhi() {
                if (!/^[0-9]*$/.test(this.inputKoutuhi)) {
                    this.koutuhiError = true;
                } else {
                    this.koutuhiError = false;
                }
            },
            INSERT() {
                const birth = this.selectedYear + '/' + this.selectedMonth + '/' + this.selectedDay;
                console.log('birth:', birth);
                console.log('age:', this.selectedAge);
                console.log('gender:', this.selectedGender);
                this.$router.push({
                    name: 'ins_Confi', params: {
                        empId: this.inputEmpId, empName: this.inputEmpName,
                        birth: birth, age: this.selectedAge, gender: this.selectedGender,
                        kihonkyu: this.inputKihonkyu,koutuhi: this.inputKoutuhi,
                    }
                });
            },
        },
        }

</script>