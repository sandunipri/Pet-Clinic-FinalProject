$(document).ready(function () {

    $('#saveVet').click(function () {
        let token = localStorage.getItem('token');
        let formData = new FormData($('#addVetForm')[0]);
        console.log("saveVet");

        console.log(token)
        console.log(formData)

        $.ajax({
            url: "http://localhost:8080/api/v1/veterinarian/save",
            type: "POST",
            data:formData,
            headers : {
                "Authorization": 'Bearer ' + token
            },
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                Swal.fire({
                    title: "Success!",
                    icon: "success",
                    text: "Vet Added Successfully!"
                }).then(() => {
                  window.location.href = 'vet.html';
                });
            },
            error: function (data) {
                alert("Vet Adding Failed");
            }
        })
    })

})
