
$(document).ready(function (){
    $('#regBtn').click(function (){
        let Email = $('#exampleInputEmail1').val();
        let Password = $('#exampleInputPassword1').val();
        let name = $('#UserName').val();
        let role = $('#UserRole').val();
        let address = $('#UserAddress').val();
        let telNo = $('#UserTelNo').val();

        $.ajax({
            url :"http://localhost:8080/api/v1/user/register",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify({
                "email" : Email,
                "password" : Password,
                "name" :name,
                "role" : role,
                "address" : address,
                "telNo" : telNo

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