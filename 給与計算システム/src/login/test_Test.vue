<template>
    <div>
        <v-text-field label="社員ID"
                      clearable
                      v-model="inputEmpId"
                      @input="checkEmpId"
                      counter="4"
                      hint="社員IDをA001～Z999の間の半角英数字で入力して下さい。"
                      class="custom-hint-style"></v-text-field>
        <p v-if="idError" class="error-message">有効な社員IDを入力してください。</p>

        <v-btn @click="INSERT" :disabled="isButtonDisabled">登録</v-btn>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                inputEmpId: "",
                idError: false
            };
        },
        computed: {
            isButtonDisabled() {
                return (
                    this.inputEmpId.length < 4 ||
                    this.idError ||
                    this.inputEmpId === "" ||
                    this.inputEmpId.length < 3
                );
            }
        },
        methods: {
            checkEmpId() {
                this.idError = false; // エラーメッセージを初期化
                if (this.inputEmpId.length >= 1 && this.inputEmpId.length <= 3) {
                    this.isButtonDisabled = true; // 1~3文字の時にボタンを非活性化
                } else {
                    // それ以外の場合にエラーチェック
                    if (!/^[A-Z]{1}[0-9]{3}$/.test(this.inputEmpId) || /^(?!.*000).+$/.test(this.inputEmpId)) {
                        this.idError = true;
                    }
                    this.isButtonDisabled = false; // ボタンを活性化
                }
            },
            INSERT() {
                // 登録処理
            }
        }
    };
</script>