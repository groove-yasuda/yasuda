
       
        // 利用者氏名の桁数チェック
        function UN_DIG_CHK(un_Exist_Chk, btn_Act) {
            var user_Name = $("#user_Name").val();

            $.ajax({
                url: "http://localhost:8080/UN_DIG_CHK",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    scrn_Var: {user_Name : user_Name},
                })
            }).done(function(un_Dig_Result) {
                var elem = "";
                if (un_Dig_Result == true) //桁数が合っている場合
                {
                    $("#user_Name").css("background-color", "white");
                    flag_UN = true;
                    $("#UN_DIG_EM").html(elem);
                } 
                else if (un_Dig_Result == false)//桁数が間違っている場合
                {
                    $("#user_Name").css("background-color", "red");
                    elem = "<font color=red> ";
                    elem += "<li>" + "利用者氏名の桁数が既定の桁数ではありません" + "</li>";
                    elem += "</font>";
                    $("#UN_DIG_EM").html(elem);
                    flag_UN = false;
                }

				un_Exist_Chk(btn_Act)

            }).fail(function() {
                console.log("fail");
            });
        }

        // 利用者氏名の存在チェック
        function UN_EXIST_CHK(un_Call_Back) {
            var user_Name = $("#user_Name").val();

            $.ajax({
                url: "http://localhost:8080/UN_EXIST_CHK",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    scrn_Var: {user_Name: user_Name},
                })
            }).done(function(un_Exist_Result) {
                var elem = "";

                if (un_Exist_Result == true) //入力された値が存在する場合
                {
                    $("#user_Name").css("background-color", "white");
                    flag_UN = true;
                    $("#UN_EXIST_EM").html(elem);
                } 
                else if (un_Exist_Result == false)//入力された値が存在していない場合
                 {
                    $("#user_Name").css("background-color", "red");
                    elem = "<font color=red> ";
                    elem += "<li>" + "利用者の氏名が登録されていません" + "</li>";
                    elem += "</font>";
                    $("#UN_EXIST_EM").html(elem);
                    flag_UN = false;
                }
                                  
			un_Call_Back(flag_UN);
                
            }).fail(function(flag_UN) {
                console.log("fail");
            });
        }
        
        
        
        