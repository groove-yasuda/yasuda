<template>
    <v-app id="inspire">
        <v-sheet color="#90A4AE"
                 class="pa-4" height= 20%>






        </v-sheet>

        <v-sheet color="#616161"
                 height= 100%>
            <v-list dense >
                <v-list-item-group>
                    <router-link v-for="(link, index) in links" :key="index" :to="`/${link[2].toLowerCase()}`" :exact="true" style="color: #616161;">
                        <v-list-item>
                            <v-list-item-icon>
                                <v-icon style="color: white;">{{ link[0] }}</v-icon>
                            </v-list-item-icon>
                            <v-list-item-title v-html="link[1]" style="color: white;"></v-list-item-title>
                        </v-list-item>
                    </router-link>
                </v-list-item-group>
            </v-list>
        </v-sheet>

    </v-app>
</template>

<style scoped>
    .v-progress-circular {
        margin: 1rem;
    }
</style>

<script>
    export default {
        data() {
            return {
                links: [
                    ['mdi-account-plus-outline', '登録', 'ins_inp'],
                    ['mdi-account-minus-outline', '削除', 'dele_inp'],
                    ['mdi-account-search-outline', '検索', 'sele_inp'],
                ],
            };
        },
        props:['param']
    };
</script>

<style>

</style>