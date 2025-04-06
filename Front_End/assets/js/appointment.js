$(document).ready(function () {

    function loadAllPetsByUser() {
    let token = localStorage.getItem('token');

    $.ajax({
        url: "http://localhost:8080/api/v1/pet/getPetsFromUser",
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            $('#petList').empty(); // Clear the existing pets

            const petSelect = $('#petList');
            petSelect.empty().append('<option value="">Select Pet</option>');

            for (let i = 0; i < response.data.length; i++) {

                const pet = response.data[i];
                petSelect.append(`<option value="${pet.petId}">${pet.petName}</option>`);


                petSelect.append(`
                     <label for="petList" class="form-label">Pet Name :</label>
                        <select class="form-select" id="petList" required>
                            <option value="">Select Pet</option>
                        </select>
                `);
            }

        },
        error: function (error) {
            console.log(error);
        }
    });
}

    $('#bookAppointment').click(function () {
        let token = localStorage.getItem('token');
        console.log("save Appointment");
        console.log(token)

        $.ajax({
            url: "http://localhost:8080/api/v1/appointment/save",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "date" : $('#date').val(),
                "time" : $('#time').val(),
                "reason": $('#reason').val(),
                "phone" : $('#phone').val(),
                "petId" : $('#petList').val(),
                "vetId" : $('#doctor').val()
            }),
            headers: {
                "Authorization": 'Bearer ' + token
            },

            success: function (data) {
                Swal.fire({
                    title: "Success!",
                    icon: "success",
                    text: "Appointment Booked Successfully!"
                }).then(() => {
                    window.location.href = 'client.html';
                });
            },
            error: function (data) {
                alert("Appointment Booking Failed");
            }
        })
    })

loadAllPetsByUser();
})