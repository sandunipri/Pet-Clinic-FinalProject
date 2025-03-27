$(document).ready(function () {
    $('#regBtn').click(function () {
        let formData = new FormData($('#registerForm')[0]);

        $.ajax({
            url: "http://localhost:8080/api/v1/client/register",
            type: "POST",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                alert("Register Success");
            },
            error: function (data) {
                alert("Register Failed");
            }
        })

    })
})