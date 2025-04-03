$(document).ready(function () {
    $('#logoutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

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

    allUsers();
    allVets();
    allPets();


});