$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    const userId = localStorage.getItem('userId');

    function loadProfile() {
        let token = localStorage.getItem('token');
        console.log("loadProfile");

        $.ajax({
            url: "http://localhost:8080/api/v1/user/getProfile",
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (response) {

                //navigation bar
                $("#profileImage").attr("src", "../" + response.data.profileImage);
                $('#userName').text(response.data.name);

                //load side profile
                $("#userProfileImage").attr("src", "../" + response.data.profileImage);
                $('#user_name').text(response.data.name);
                $('#email').text(response.data.email);

                //welcome message
                $('#displayName').text(response.data.name);

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function loadAllPets() {
        let token = localStorage.getItem('token');

        const currentDate = new Date().toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });

        $.ajax({
            url: "http://localhost:8080/api/v1/pet/getPetsFromUser",
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (response) {

                $('#petList').empty(); // Clear the existing pets

                for (let i = 0; i < response.data.length; i++) {

                    $('#petList').append(`
                        <div class="col-md-5">
                        <div class="pet-card p-3 h-100">
                            <div class="d-flex">
                                <img id="petImages" src="../${response.data[i].petImage}"
                                     alt="Max" class="rounded-circle me-4 object-fit-cover " width="80" height="80">
                                <div>
                                    <h5 id="petType" class="mb-1">${response.data[i].petName} <span class="badge badge-teal rounded-pill">${response.data[i].species}</span></h5>
                                    <p id="petbreed" class="text-muted small mb-1">${response.data[i].breed}</p>
                                    <p id="petage" class="text-muted small mb-1">Age : ${response.data[i].age} years</p>
                                    <p class="small mb-2">Last visit: <span class="pet-date">${currentDate}</span></p>                                    
                                    <div class="btn-group btn-group-sm">
                                        <button type="button" id="deletePet" class="btn btn-outline-primary me-3">DELETE</button>
                                            <button
                                                    class="editPetDetails btn btn-outline-primary"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#editPetModal"
                                                    data-petimage =  "${response.data[i].petImage}"
                                                    data-petid = "${response.data[i].petId}"
                                                    data-petname = "${response.data[i].petName}"
                                                    data-pettype = "${response.data[i].species}"
                                                    data-petbreed = "${response.data[i].breed}"
                                                    data-petage = "${response.data[i].age}"
                                                    data-petbod = "${response.data[i].birthDate}"
                                                    
                                            >
                                                Edit
                                            </button>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>   
                    `)
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    $(document).on("click", ".editPetDetails", function () {
        console.log("Edit Pet button clicked");
        let pet = {
            petImage: $(this).data("petimage"),
            petId: $(this).data("petid"),
            petName: $(this).data("petname"),
            species: $(this).data("pettype"),
            breed: $(this).data("petbreed"),
            age: $(this).data("petage"),
            birthDate: $(this).data("petbod")


        };
        console.log(pet)
        viewPetProfile(pet);

    });

    function viewPetProfile(pet) {
        console.log("viewPetProfile function called");
        $('#editPetImagePreview').attr('src', '../' + pet.petImage);
        $('#petName').val(pet.petName);
        $('#petId').val(pet.petId);
        $('#species').val(pet.species);
        $('#breed').val(pet.breed);
        $('#age').val(pet.age);
        $('#birthDate').val(pet.birthDate);

        $('#editPetModal').modal('show');

    }

    $(document).on("click", "#deletePet", function () {
        let token = localStorage.getItem('token');
        let petId = $(this).closest('.pet-card').find('.editPetDetails').data("petid");
        $.ajax({
            url: `http://localhost:8080/api/v1/pet/deletePet?petId=${petId}`,
            type: "DELETE",
            contentType: "application/json",
            headers : {
                "Authorization": 'Bearer ' + token
            },
            success: function (data) {
                alert("Pet Deleted Success");
                loadAllPets();
            },
            error: function (data) {
                alert("Pet Deleted Failed");
            }
        });

    });

    function loadAppointmentFromUser() {
        let token = localStorage.getItem('token');
        console.log("loadAppointmentFromUser");

        $.ajax({
            url: "http://localhost:8080/api/v1/appointment/getAllAppointmentsFromUser",
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },

            success: function (response) {
                console.log(response.data);
                $('#appointmentList').empty(); // Clear the existing appointments

                for (let i = 0; i < response.data.length; i++) {
                    const appointment = response.data[i];
                    const vetName = `${appointment.veterinarian.title} ${appointment.veterinarian.firstName} ${appointment.veterinarian.lastName}`;


                    $('#appointmentList').append(`
                     <div class="list-group-item">
                         <div class="list-group-item" data-appointment-id="${appointment.id}">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div
                                        <h6 class="mb-1">Appointment Reason :${appointment.reason}</h6>
                                        <h6 class="mb-1">Pet Name :${appointment.pet.petName}</h6>
                                        <p class="small text-muted mb-1">
                                            <i class="far fa-calendar me-1"></i> ${appointment.date}
                                        </p>
                                        <p class="small text-muted mb-0">
                                            <i class="fas fa-user-md me-1"></i> ${vetName}
                                        </p>
                                    </div>
                                    <div class="btn-group">
                                    <button id="deleteAppointment" class="btn btn-sm btn-outline-primary">DELETE</button>
                                    <button class="btn btn-sm btn-outline-primary reschedule-btn"
                                            data-bs-toggle="modal"
                                            data-bs-target="#updateAppointmentModal"
                                            data-appointment-id="${appointment.id}"
                                            data-appointment-date="${appointment.date}"
                                            data-appointment-time="${appointment.time}"
                                            data-appointment-reason="${appointment.reason}">
                                        <i class="fas fa-calendar-alt me-1"></i> Reschedule
                                    </button>
                                    </div>
                                </div>
                         </div>
                     </div>`)
                }
            },

        });
    }

    $(document).on("click",".reschedule-btn",function (){
        console.log(" Reschedule button click")

        let appointment = {
            id : $(this).data("appointment-id"),
            date: $(this).data("appointment-date"),
            time :$(this).data("appointment-time"),
            reason :$(this).data("appointment-reason")
        };

    console.log(appointment)
    viewAppointmentProfile(appointment);
    });

    function viewAppointmentProfile(appointment){

        console.log("view appointment function called");
        $('#updateAppointmentId').val(appointment.id);
        $('#updateAppointmentDate').val(appointment.date);
        $('#updateAppointmentTime').val(appointment.time);
        $('#updateAppointmentReason').val(appointment.reason);

        $('#updateAppointmentModal').modal('show');

    }
    $('#updateAppointmentBtn').click(function (){
        console.log("updated btm clicked")
        let token = localStorage.getItem('token')

        console.log(token)

        let appointmentId = $('#updateAppointmentId').val();
        let date = $('#updateAppointmentDate').val();
        let time = $('#updateAppointmentTime').val();
        let reason = $('#updateAppointmentReason').val();

        console.log(date)
        console.log(time)
        console.log(reason)

        $.ajax({
            url:"http://localhost:8080/api/v1/appointment/updateAppointment",
            type : "PUT",
            contentType: "application/json",
            data: JSON.stringify({
                "id" : appointmentId,
                "date" : date,
                "time" : time,
                "reason": reason
            }),
            headers: {
                "Authorization": 'Bearer ' + token
            },

            success : function (data){
                alert("Appointment Updated successfully");
            },
            error : function (data){
                alert("Appointment Updated Failed")
            }



        })
    })

    $(document).on("click", "#deleteAppointment", function () {
        let token = localStorage.getItem('token');
        let appointmentID = $(this).closest('.list-group-item').data("appointment-id");
        console.log(appointmentID)
        $.ajax({
            url: `http://localhost:8080/api/v1/appointment/deleteAppointment?id=${appointmentID}`,
            type: "DELETE",
            headers : {
                "Authorization": 'Bearer ' + token
            },
            success: function (data) {
                alert("Appointment Deleted Success");
                loadAppointmentFromUser();
            },
            error: function (data) {
                alert("Appointment Deleted Failed");
            }
        });

    });

    loadProfile();
    loadAllPets();
    loadAppointmentFromUser();

});





