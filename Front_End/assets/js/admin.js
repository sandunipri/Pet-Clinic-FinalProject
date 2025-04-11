$(document).ready(function () {
    $('#logoutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

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

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function allUsers(){
        $.ajax({
            url: 'http://localhost:8080/api/v1/admin/getAllUsers',
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {

                let users = response.data;
                let tableBody = $('#user_table');
                tableBody.empty();
                users.forEach(user => {
                    console.log(user.petList);
                    tableBody.append(`
                        <tr>
                            <td>
                            <img src="../${user.profileImage}"
                                         class="user-avatar me-2">
                                         </td>
                            <td>${user.email}</td>
                            <td>${user.name}</td>
                            <td>${user.address}</td>
                            <td>${user.telNo}</td>
                            <td>${user.petCount}</td>
                            <td>
                             <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                    
                             </td>
                        </tr>
                    `);
                });
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    function allVets(){
        $.ajax({
            url: 'http://localhost:8080/api/v1/admin/getAllVeterinary',
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },

            success: function (response) {

                let vets = response.data;
                let tableBody = $('#vet_table');
                tableBody.empty();
                vets.forEach(vet => {
                    tableBody.append(`
                        <tr>
                            <td>
                            <img src="../${vet.profileImage}"
                                         class="user-avatar me-2">
                                         </td>
                            <td>${vet.email}</td>
                            <td>${vet.name}</td>
                            <td>${vet.address}</td>
                            <td>${vet.phone}</td>
                            <td>${vet.specialty}</td>
                            <td>
                           
                             <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                          
                                     <a href="viewVeterinariesProfile.html">
                                    <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                              </a>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                             </td>
                        </tr>
                    `);
                });
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    function allPets(){
        $.ajax({
            url : "http://localhost:8080/api/v1/admin/getAllPets",
            type : "GET",
            headers : {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {
                let pets = response.data;
                let tableBody = $('#pet_table');
                tableBody.empty();
                pets.forEach(pets => {
                    tableBody.append(`
                        <tr>
                            <td>
                            <img src="../${pets.petImage}"
                                         class="user-avatar me-2">
                            </td>
                            <td>${pets.petName}</td>
                            <td>${pets.species}</td>
                            <td>${pets.breed}</td>
                            <td>${pets.age}</td>
                            <td>${pets.user.name}</td>
                               <td>
                                    
                                        <button class="btn btn-sm btn-outline-primary me-1">
                                             <i class="fas fa-eye"></i>
                                        </button>
                                 
                                        <button class="btn btn-sm btn-outline-primary me-1">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-danger">
                                            <i class="fas fa-trash"></i>
                                        </button>
                             </td>
                        </tr>
                    `);
                });
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }

        })

    }

    function allAppointments(){
        console.log("Get All Appointments")
        let token = localStorage.getItem('token');

        $.ajax({
            url : "http://localhost:8080/api/v1/appointment/getAllAppointments",
            type : "GET",
            headers : {
                "Authorization": 'Bearer ' + token
            },
            success: function (response){
                let appointments = response.data
                let tableBody = $('#appointment_table');
                appointments.forEach(appointment => {
                    console.log("appointment id :" + appointment.id)
                    tableBody.append(`
                            <tr>
                                <td>${appointment.id}</td>
                                <td>${appointment.date}<br>${appointment.time}</td>
                                <td>${appointment.pet.petName}</td>
                                <th>${appointment.veterinarian.title} ${appointment.veterinarian.firstName} ${appointment.veterinarian.lastName}</th>
                                <td>${appointment.user.name}</td>
                                <td>${appointment.reason}</td>
                                <td>
                                    <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </td>
                            </tr>

                    `);
                })

            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        })
    }

    function loadAllUsersCount(){
        console.log("loadAllUsersCount")
        $.ajax({
            url:"http://localhost:8080/api/v1/user/getAllUsersCount",
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {
                $('#userCount').text(response.data);
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    function loadAllVetCount(){
        console.log("loadAllVetCount")
        $.ajax({
            url:"http://localhost:8080/api/v1/veterinarian/getAllVetsCount",
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {
                $('#vetCount').text(response.data);
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    function loadAllPetsCount(){
        console.log("loadAllPetsCount")
        $.ajax({
            url:"http://localhost:8080/api/v1/pet/getAllPetsCount",
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {
                $('#petCount').text(response.data);
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    function loadAllAppointmentsCount(){
        console.log("loadAllAppointmentsCount")
        $.ajax({
            url:"http://localhost:8080/api/v1/appointment/getAppointmentsCount",
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {
                $('#appointmentCount').text(response.data);
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }

    loadProfile();
    allUsers();
    allVets();
    allPets();
    allAppointments();
    loadAllUsersCount();
    loadAllVetCount();
    loadAllPetsCount();
    loadAllAppointmentsCount();


});