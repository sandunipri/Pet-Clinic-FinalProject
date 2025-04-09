$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    $('#addPet').click(function () {
        console.log("Add Pet button clicked");
        let token = localStorage.getItem('token');
        if (!token) {
            alert("Please log in to add a pet.");
            return;
        }

        let formData = new FormData($('#pet-registration-form')[0]);
        // Get the selected pet type
        const petType = document.querySelector('input[name="species"]:checked').value;
        console.log("Selected Pet Type:", petType);

        console.log(formData);

        $.ajax({
            url: "http://localhost:8080/api/v1/pet/save",
            type: "POST",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (data) {
                alert("Pet Added Success");
            },
            error: function (data) {
                alert("Pet Added Failed");
            }
        });
    })

    $('#updatePet').click(function () {
            console.log("Update Pet button clicked");
            let token = localStorage.getItem('token');

            let formData = new FormData($('#editPetModal')[0]);
            console.log(formData);

            $.ajax({
                url: "http://localhost:8080/api/v1/pet/updatePet",
                type: "PUT",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (data) {
                    alert("Pet Updated Success");
                    window.location.reload();
                },
                error: function (data) {
                    alert("Pet Updated Failed");
                }
            });

        })

});