$(document).ready(function () {
    $('#profileImage').on('change', function (event) {
        const file = $(this).prop('files')[0];
        const preview = $('#photoPreview');

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                preview.attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        } else {
            preview.css('display', 'none');
        }
    });

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
                window.location.href = "loginForm.html";
            },
            error: function (data) {
                alert("Register Failed");
            }
        })

    })
})