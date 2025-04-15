$(document).ready(function () {
    // CSS for error messages (add to your stylesheet)
    const errorStyle = `
        <style>
            .error-message {
                color: #dc3545;
                font-size: 0.875rem;
                margin-top: 0.25rem;
                display: none;
            }
            .input-error {
                border-color: #dc3545 !important;
            }
        </style>
    `;

    $('head').append(errorStyle);

    // Add error message elements under each input
    $('.form-control').each(function() {
        $(this).after(`<div class="error-message" id="${$(this).attr('id')}-error"></div>`);
    });

    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    function showError(fieldId, message) {
        $(`#${fieldId}`).addClass('input-error');
        $(`#${fieldId}-error`).text(message).show();
    }

    function clearErrors() {
        $('.error-message').hide();
        $('.form-control').removeClass('input-error');
    }

    $('#addPet').click(function (e) {
        e.preventDefault();
        clearErrors();

        let isValid = true;
        const petWeightRegex = /^[0-9]+(\.[0-9]{1,2})?$/;
        const petAgeRegex = /^[0-9]+$/;

        // Validate each field
        if (!$('#petName').val()) {
            showError('petName', 'Pet name is required');
            isValid = false;
        }

        if (!$('#age').val() || !petAgeRegex.test($('#age').val())) {
            showError('age', 'Valid pet age is required');
            isValid = false;
        }

        if (!$('#breed').val()) {
            showError('breed', 'Pet breed is required');
            isValid = false;
        }

        if (!$('#weight').val() || !petWeightRegex.test($('#weight').val())) {
            showError('weight', 'Valid pet weight is required');
            isValid = false;
        }

        if (!$('#gender').val()) {
            showError('gender', 'Pet gender is required');
            isValid = false;
        }

        if (!$('#petImage').val()) {
            showError('petImage', 'Pet image is required');
            isValid = false;
        }

        if (!isValid) return;

        let token = localStorage.getItem('token');
        if (!token) {
            Swal.fire('Error', 'Please log in to add a pet', 'error');
            return;
        }

        let formData = new FormData($('#pet-registration-form')[0]);

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
                    window.location.href = "client.html";
                });
            },
            error: function (xhr) {
                const errorMsg = xhr.responseJSON?.message || 'Failed to add pet. Please try again.';
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: errorMsg
                });
            }
        });
    });

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
                title: 'Errors',
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