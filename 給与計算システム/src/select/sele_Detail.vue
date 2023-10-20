<template>
    <v-app>
        <v-main>
            <v-container>
                <v-icon>mdi-account-search</v-icon>
                <p>社員情報検索</p>
                <p>表示画面</p>


                <template>
                    <p v-if="isConditionMet">
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <v-data-table :headers="headers"
                                                  :items="desserts"
                                                  :items-per-page="itemsPerPage"
                                                  class="elevation-10"
                                                  height="100px"
                                                  :footer-props="{
                                          showFirstLastPage: true,
                                          firstIcon: 'mdi-arrow-collapse-left',
                                          lastIcon: 'mdi-arrow-collapse-right',
                                          prevIcon: 'mdi-minus',
                                          nextIcon: 'mdi-plus'}">


                                        <template v-slot:item="{ item }">
                                            <tr>
                                                <td class="text-start" :style="{'color': 'black'}">{{ item['社員ID'] }}</td>
                                                <td class="text-start" :style="{'color': 'black'}">{{ item['社員名'] }}</td>
                                                <td class="text-start" :style="{'color': 'black'}">{{ item['生年月日'] }}</td>
                                                <td class="text-start" :style="{'color': 'black'}">{{ item['年齢'] }}</td>
                                                <td class="text-start" :style="{'color': 'black'}">{{ item['性別'] }}</td>
                                            </tr>
                                        </template>
                                    </v-data-table>
                                </v-col>
                            </v-row>

                            <v-row>
                                <v-col cols="12">
                                    <hot-table :data="karam" :settings="set" colWidths="200" rowHeights="45"></hot-table>
                                    <hot-table :data="data" :settings="settings" colWidths="200" rowHeights="45"></hot-table>
                                    <br>
                                    <hot-table :data="karam2" :settings="set" colWidths="200" rowHeights="45"></hot-table>
                                    <hot-table :data="data2" :settings="settings" colWidths="200" rowHeights="45"></hot-table>
                                    <br>
                                    <hot-table :data="karam3" :settings="set" colWidths="200" rowHeights="45"></hot-table>
                                    <hot-table :data="data3" :settings="settings" colWidths="200" rowHeights="45"></hot-table>
                                    <br>
                                    <hot-table :data="karam4" :settings="set" colWidths="200" rowHeights="45"></hot-table>
                                    <hot-table :data="data4" :settings="settings" colWidths="200" rowHeights="45"></hot-table>
                                </v-col>
                            </v-row>
                        </v-container>
                    </p>
                    <p v-else>検索結果が存在しません</p>
                </template>

            </v-container>


            <v-row justify="center">
                <v-col cols="auto">
                    <v-btn v-on:click="RETURN">戻る</v-btn>
                </v-col>
            </v-row>


        </v-main>
    </v-app>
</template>


<style type="text/css">
    p {
        font-family: 'Century', Times, serif;
        color: black;
    }

    .error-message {
        color: red;
    }

    .table-style {
        width: 75%;
        border-collapse: collapse;
    }

        .table-style th {
            padding: 8px;
            text-align: center;
            border: 5px solid #4e4e4e;
        }

        .table-style td {
            padding: 8px;
            text-align: center;
            border: 1px solid #4e4e4e;
        }
    .font-size {
         font-size: 20px; 
    }
</style>

<script>
    import { HotTable } from '@handsontable/vue';
    import { registerAllModules } from 'handsontable/registry';
    import Handsontable from 'handsontable';
    import 'handsontable/dist/handsontable.full.css';
    import axios from 'axios';

    registerAllModules();


    export default {
        data() {
            return {
                itemsPerPage: 1,
                search_Prime: '',
                search_Option: '',
                id_Order: '',
                name_Order: '',
                search_Target: '',
                response_Data: [],
                syainID: '',
                isConditionMet: false,
                desserts: [],
                headers: [
                    { text: '社員ID' },
                    { text: '社員名' },
                    { text: '生年月日' },
                    { text: '年齢' },
                    { text: '性別' },
                ],
                data: [
                    ["就業日数", "出勤日数", "労働時間", "欠勤日数",""],
                    [],
                    ["残業時間", "休日出勤日数"],
                    [],
                ],
                data2: [
                    ["基本給", "通勤手当", "残業代", "固定残業代", "合計"],
                    [],
                ],
                data3: [
                    ["雇用保険", "健康保険", "厚生年金", "","社会保険合計"],
                    [],
                    ["所得税", "住民税", "", "", "税額合計"],
                    [],
                ],
                data4: [
                    ["総支給額", "", "控除総額","", "差引支給額"],
                    [],
                ],
                karam: [
                    ["勤怠"],
                ],
                karam2: [
                    ["給与"],
                ],
                karam3: [
                    ["控除"],
                ],
                karam4: [
                    ["合計"],
                ],
                settings: {
                    readOnly: true,
                    licenseKey: 'non-commercial-and-evaluation',
                    className: 'font-size',
                    cells: (row) => {
                        if (row === 0 || row === 2) {
                            return {
                                renderer: this.customRenderer,
                            };
                        }
                    },
                },
                set: {
                    readOnly: true,
                    licenseKey: 'non-commercial-and-evaluation',
                    className: 'font-size',
                    cells: (row) => {
                        if (row === 0 || row === 2) {
                            return {
                                renderer: this.customKaramRenderer,
                            };
                        }
                    },
                },
            };
        },
        created() {
            // ルーターからパラメータを取得してデータに代入
            this.syainID = this.$route.params.syainID;
            console.log('クリックされた行の社員ID:', this.syainID);
            // ルーターからパラメータを取得してデータに代入
            this.search_Prime = this.$route.params.search_Prime;
            this.search_Option = this.$route.params.search_Option;
            this.id_Order = this.$route.params.id_Order;
            this.name_Order = this.$route.params.name_Order;
            this.input_Emp_Search = this.$route.params.input_Emp_Search;
            this.search_Target = this.$route.params.search_Target;
        },
        mounted() {
            axios
                .request({
                    method: 'POST',
                    url: 'http://localhost:8080/select_Private_Func',
                    data: {
                        search_Prime: 'syainID',
                        search_Option: 'front',
                        id_Order: 'ASC',
                        name_Order: 'ASC',
                        input_Emp_Search: this.syainID,
                        search_Target: 'ID'
                    }
                })
                .then((response) => {
                        const data = response.data; // レスポンスデータを取得

                        const list0 = data["key0"]; // "key0"のリストを取得
                        const list1 = data["key1"]; // "key1"のリストを取得

                    let sousikyu;


                    list0.forEach((item) => {
                        const parts = item.split(", ");
                        const syainID = parts[0].split("=")[1];
                        const syainNAME = parts[1].split("=")[1];
                        const birth = parts[2].split("=")[1];
                        const age = parts[3].split("=")[1];
                        const gender = parts[4].split("=")[1];

                        this.desserts.push({
                            '社員ID': syainID,
                            '社員名': syainNAME,
                            '生年月日': birth,
                            '年齢': age,
                            '性別': gender,
                        });

                        const basic_salary = parseFloat(parts[5].split("=")[1]);
                        const Transportation_expenses = parseFloat(parts[6].split("=")[1]);
                        const overtime_pay = parseFloat(parts[7].split("=")[1]);
                        const Fixed_overtime_pay = parseFloat(parts[8].split("=")[1]);

                        this.data2[1][0] = `¥${basic_salary.toLocaleString()}`;
                        this.data2[1][1] = `¥${Transportation_expenses.toLocaleString()}`;
                        this.data2[1][2] = `¥${overtime_pay.toLocaleString()}`;
                        this.data2[1][3] = `¥${Fixed_overtime_pay.toLocaleString()}`;
                        this.data2[1][4] = `¥${(basic_salary + Transportation_expenses + overtime_pay + Fixed_overtime_pay).toLocaleString()}`;

                        sousikyu = basic_salary + Transportation_expenses + overtime_pay + Fixed_overtime_pay;



                        const syuugyou = parseFloat(parts[9].split("=")[1]);
                        const syukkin = parseFloat(parts[10].split("=")[1]);
                        const roudou = parts[11].split("=")[1];
                        const kekkin = parseFloat(parts[12].split("=")[1]);
                        const zangyou = parts[13].split("=")[1];
                        const kyuusyutu = parts[14].split("=")[1];




                        this.isConditionMet = true;

                        this.data[1][0] = syuugyou + ' 日';
                        this.data[1][1] = syukkin + ' 日';
                        this.data[1][2] = roudou + ' 時間';
                        this.data[1][3] = kyuusyutu + ' 日';
                        this.data[3][0] = zangyou + ' 時間';
                        this.data[3][1] = kekkin + ' 日';


                    });

                    list1.forEach((item) => {
                        const parts = item.split(", ");
                        const koyouhokenritu = parseFloat(parts[0].split("=")[1]);
                        const kouseinenkinhoken = parseFloat(parts[1].split("=")[1]);
                        const hokenritu_iryou = parseFloat(parts[3].split("=")[1]);
                        const syotokuzeikiso = parseFloat(parts[4].split("=")[1]);
                        const zyuminzeikiso = parseFloat(parts[4].split("=")[1]);
                        const syotokuzeiritu = parseFloat(parts[6].split("=")[1]);
                        const zyuminzeiritu = parseFloat(parts[7].split("=")[1]);
                        const kouzyo = parseFloat(parts[8].split("=")[1]);
                        const koyouhoken = sousikyu * koyouhokenritu * 0.01;
                        const kenkouhoken = sousikyu * hokenritu_iryou * 0.5 * 0.01;
                        const kouseinenkin = sousikyu * kouseinenkinhoken * 0.5 * 0.01;//
                        const kyuyosyotoku = (sousikyu * 12) - kouzyo;
                        const kazeikyuyo_syotoku = (kyuyosyotoku - syotokuzeikiso - (kouseinenkin * 12) - (kenkouhoken * 12) - (koyouhoken * 12))/12;
                        const syotokuzei = kazeikyuyo_syotoku * syotokuzeiritu *0.01;
                        const kazeikyuyo_zyumin = (kyuyosyotoku - zyuminzeikiso - (kouseinenkin * 12) - (kenkouhoken * 12) - (koyouhoken * 12))/12;
                        const zyuminzei = kazeikyuyo_zyumin * zyuminzeiritu * 0.01;
                        const syakaihoken = koyouhoken + kenkouhoken + kouseinenkin;
                        const zeigaku = syotokuzei + zyuminzei;
                        const kouzyogaku = syakaihoken + zeigaku;
                        const sasihikisikyu = sousikyu - kouzyogaku;

                        this.data3[1][0] = `¥${koyouhoken.toLocaleString()}`;//雇用保険
                        this.data3[1][1] = `¥${kenkouhoken.toLocaleString()}`;//健康保険
                        this.data3[1][2] = `¥${Math.ceil(kouseinenkin).toLocaleString()}`;//厚生年金
                        this.data3[1][4] = `¥${Math.ceil(syakaihoken).toLocaleString()}`;//社会保険合計
                        this.data3[3][0] = `¥${Math.ceil(syotokuzei).toLocaleString()}`;//所得税
                        this.data3[3][1] = `¥${Math.ceil(zyuminzei).toLocaleString()}`;//住民税
                        this.data3[3][4] = `¥${Math.ceil(zeigaku).toLocaleString()}`;//税額合計

                        this.data4[1][0] = `¥${Math.ceil(sousikyu).toLocaleString()}`;//総支給額
                        this.data4[1][2] = `¥${Math.ceil(kouzyogaku).toLocaleString()}`;//税額合計
                        this.data4[1][4] = `¥${Math.ceil(sasihikisikyu).toLocaleString()}`;//税額合計    

                    });

                })
        },
        methods: {

            RETURN() {
                this.$router.push({
                    name: 'sele_Result', params: {
                        search_Prime: this.search_Prime, search_Option: this.search_Option,
                        id_Order: this.id_Order, name_Order: this.name_Order,
                        input_Emp_Search: this.input_Emp_Search, search_Target: this.search_Target
                    }
                });
            },
            customRenderer(instance, td) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.className = 'custom-cell'; // セルにクラス名を設定
                td.style.backgroundColor = 'gray'; // セルの背景色を設定
                td.style.color = 'black'; // セルのテキスト色を設定
                td.style.fontSize = '20px';
            },
            customKaramRenderer(instance, td) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.className = 'custom-cell'; // セルにクラス名を設定
                td.style.backgroundColor = 'silver'; // セルの背景色を設定
                td.style.color = 'black'; // セルのテキスト色を設定
                td.style.fontSize = '20px';
            },
        },
        components: {
            HotTable,
        },
    }

</script>