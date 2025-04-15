$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    $('#addPet').click(function (e) {
        e.preventDefault();

        let petName = $('#petName').val()
        let petAge = $('#age').val()
        let petBreed = $('#breed').val()
        let petWeight = $('#weight').val()
        let petGender = $('#gender').val()
        let petImage = $('#petImage').val()

        const petWeightRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
        const petAgeRegex = /^[0-9]+$/;

        let errorMessages = [];

        $('input, select').removeClass('input-error');

        if (!petName) {
            errorMessages.push("Pet name is required.");
            $('#petName').addClass('input-error');
        }
        if (!petAge || !petAgeRegex.test(petAge)) {
            errorMessages.push("Valid pet age is required.");
            $('#petAge').addClass('input-error');
        }
        if (!petBreed) {
            errorMessages.push("Pet breed is required.");
            $('#petBreed').addClass('input-error');
        }
        if (!petWeight || !petWeightRegex.test(petWeight)) {
            errorMessages.push("Valid pet weight is required.");
            $('#petWeight').addClass('input-error');
        }
        if (!petGender){
            errorMessages.push("PetGender is required")
            $('#petGender').addClass('input-error');
        }
        if (!petImage) {
            errorMessages.push("Pet image is required.");
            $('#petImage').addClass('input-error');
        }
        if (errorMessages.length > 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Validation Errors',
                html: `<ul style="text-align:left;">${errorMessages.map(e => `<li>${e}</li>`).join('')}</ul>`
            });
            return;
        }

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
                Swal.fire({
                    icon: 'success',
                    title: 'Pet Added',
                    text: 'Your pet has been added successfully!'
                }).then(() => {
                    window.location.reload();
                    window.location.href = "client.html";
                })
            },
            error: function (data) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Failed to add pet. Please try again.'
                });
            }
        });
    })

    $('#updatePet').click(function () {
            let petName = $('#petName').val()
            let petAge = $('#age').val()
            let petBreed = $('#breed').val()
            let petWeight = $('#weight').val()

            const petWeightRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
            const petAgeRegex = /^[0-9]+$/;

            let errorMessages = [];

            $('input, select').removeClass('input-error');

            if (!petName) {
                errorMessages.push("Pet name is required.");
                $('#petName').addClass('input-error');
            }

            if (!petAge || !petAgeRegex.test(petAge)) {
                errorMessages.push("Valid pet age is required.");
                $('#petAge').addClass('input-error');
            }

            if (!petBreed) {
                errorMessages.push("Pet breed is required.");
                $('#petBreed').addClass('input-error');
            }

            if (!petWeight || !petWeightRegex.test(petWeight)) {
                errorMessages.push("pet weight is required.");
                $('#petWeight').addClass('input-error');
            }

            if (errorMessages.length > 0) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Validation Errors',
                    html: `<ul style="text-align:left;">${errorMessages.map(e => `<li>${e}</li>`).join('')}</ul>`
                });
                return;
            }


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
                    Swal.fire({
                        icon: 'success',
                        title: 'Pet Updated',
                        text: 'Your pet has been updated successfully!'
                    }).then(() => {
                        $('#editPetModal').modal('hide');
                        window.location.reload();
                        window.location.href = "client.html";
                    })

                },
                error: function (data) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Failed to update pet. Please try again.'
                    });
                }
            });

        })

});