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

                            <v-data-table :headers="headers"
                                          :items="desserts"
                                          :items-per-page="itemsPerPage"
                                          class="elevation-10"
                                          height="600px"
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
                                        <td>
                                            <v-btn @click="onButtonClick(item)"><v-icon>mdi-account-search-outline</v-icon></v-btn>
                                        </td>
                                    </tr>
                                </template>
                            </v-data-table>
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
        color:black;
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
</style>

<script>
    import axios from 'axios';
    export default {
        data() {
            return {
                itemsPerPage: 5,
                search_Prime: '',
                search_Option: '',
                id_Order: '',
                name_Order: '',
                input_Emp_Search: '', 
                search_Target: '',
                response_Data: [],
                isConditionMet: false,
                desserts: [],
                headers: [
                    {text: '社員ID'},
                    { text: '社員名' },
                    { text: '詳細' },
                ],
            };
        },
        created() {
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
                    url: 'http://localhost:8080/select_Func',
                    data: {
                        search_Prime: this.search_Prime,
                        search_Option: this.search_Option,
                        id_Order: this.id_Order,
                        name_Order: this.name_Order,
                        input_Emp_Search: this.input_Emp_Search,
                        search_Target: this.search_Target
                    }
                })
                .then((response) => {

                    this.desserts = response.data.map((item) => {
                        const parts = item.split(", ");
                        const syainID = parts[0].split("=")[1];
                        const syainNAME = parts[1].split("=")[1];
                        this.isConditionMet = true;

                        return {
                            '社員ID': syainID,
                            '社員名': syainNAME

                        };
                    });

                })
        },
        methods: {

            RETURN() {
                this.$router.push({ name: 'sele_Inp' });
            },
            onButtonClick(item) {
                const syainID = item['社員ID'];
                console.log('クリックされた行の社員ID:', syainID);
                this.$router.push({
                    name: 'sele_Detail', params: {
                        syainID: item['社員ID'], search_Prime: this.search_Prime, search_Option: this.search_Option,
                        id_Order: this.id_Order, name_Order: this.name_Order,
                        input_Emp_Search: this.input_Emp_Search, search_Target: this.search_Target
                    }
                });
            },
        }
    }

</script>