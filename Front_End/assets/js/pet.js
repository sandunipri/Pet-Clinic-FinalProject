$(document).ready(function () {

    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    $('#addPet').click(function () {
        let formData = new FormData($('#pet-registration-form')[0]);

        $.ajax({
            url: "http://localhost:8080/api/v1/pet/save",
            type: "POST",
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                alert("Pet Added Success");
            },
            error: function (data) {
                alert("Pet Added Failed");
            }
        })

    })

});