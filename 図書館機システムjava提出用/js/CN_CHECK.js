 
 
 
 		// 図書カード番号の桁数チェック
        function CN_DIG_CHK(cn_Exist_Chk, btn_Act) {
            var card_Number = $("#card_Number").val();

            $.ajax({
                url:"http://localhost:8080/CN_DIG_CHK",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    scrn_Var: {card_Number : card_Number},
                })
            }).done(function(cn_Dig_Result) {
                var elem = "";

                if (cn_Dig_Result == true) //桁数が合っている場合
                {
                    $("#card_Number").css("background-color", "white");
                    $("#CN_DIG_EM").html(elem);
                    flag_CN = true;
                } 
                else if (cn_Dig_Result == false) //桁数が間違っている場合
                {
                    $("#card_Number").css("background-color", "red");
                    elem = "<font color=red> ";
                    elem += "<li>" + "図書カード番号の桁数が既定の桁数ではありません" + "</li>";
                    elem += "</font>";
                    $("#CN_DIG_EM").html(elem);
                    flag_CN = false;
                }

				cn_Exist_Chk(btn_Act)

            }).fail(function() {
                console.log("fail");
            });
        }
        

        // 図書カード番号の存在チェック
        function CN_EXIST_CHK(cn_Call_Back) {
            var card_Number = $("#card_Number").val();
            $.ajax({
                url: "http://localhost:8080/CN_EXIST_CHK",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    scrn_Var: {card_Number : card_Number},
                })
            }).done(function(cn_Exist_Result) {
                var elem = "";

                if (cn_Exist_Result == true) //入力された値が存在している場合
                {
                    $("#card_Number").css("background-color", "white");
                    $("#CN_EXIST_EM").html(elem);
                    flag_CN = true;

                } 
                else if (cn_Exist_Result == false)//入力された値が存在していない場合
                {
                    $("#card_Number").css("background-color", "red");
                    elem = "<font color=red> ";
                    elem += "<li>" + "図書カード番号が登録されていません" + "</li>";
                    elem += "</font>";
                    $("#CN_EXIST_EM").html(elem);
                    flag_CN = false;
                }
                
				cn_Call_Back(flag_CN);
                
            }).fail(function() {
                console.log("fail");
            });
        }
  