$(document).ready(function (){
    $('#regBtn').click(function (){
        let Email = $('#exampleInputEmail1').val();
        let Password = $('#exampleInputPassword1').val();
        let name = $('#UserName').val();
        let role = $('#UserRole').val();

        $.ajax({
            url :"http://localhost:8080/api/v1/user/register",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify({
                "email" : Email,
                "password" : Password,
                "name" :name,
                "role" : role

            }),
            success : function (data){
                alert("Register Success");
                window.location.href = "loginForm.html";
            },
            error : function (data){
                alert("Register Failed");
            }
        })

    })
})