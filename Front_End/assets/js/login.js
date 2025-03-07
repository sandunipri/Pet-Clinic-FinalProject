$(document).ready(function (){
    $('#loginBtn').click(function (){
        let Email = $('#exampleInputEmail1').val();
        let Password = $('#exampleInputPassword1').val();

        $.ajax({
            url :"http://localhost:8080/api/v1/auth/authenticate",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify({
                "email" : Email,
                "password" : Password,
                "name" :"USER",
                "role" : "USER"

            }),
            success: function (res){
                console.log(res);
                alert('Login Success');
                window.localStorage.setItem('token',res.data.token);
                window.location.href = 'home.html';
                window.localStorage.clear();
            },
            error: function (res){
                console.log(res);
                alert('Login Failed');
            }
        });
    });
});