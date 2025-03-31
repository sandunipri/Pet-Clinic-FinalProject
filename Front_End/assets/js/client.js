$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    // load profile image for the navigation bar
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
                                <img id="petImage" src="../${response.data[i].petImage}"
                                     alt="Max" class="rounded-circle me-5 " width="80" height="80">
                                <div>
                                    <h5 id="petType" class="mb-1">${response.data[i].petName} <span class="badge badge-teal rounded-pill">${response.data[i].species}</span></h5>
                                    <p id="breed" class="text-muted small mb-1">${response.data[i].breed}</p>
                                    <p id="age" class="text-muted small mb-1">Age : ${response.data[i].age} years</p>
                                    <p class="small mb-2">Last visit: <span class="pet-date">${currentDate}</span></p>                                    <div class="btn-group btn-group-sm">
                                        <a><button class="btn btn-outline-primary">View</button></a>
                                           <a>
                                            <button
                                                    class="btn btn-outline-primary"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#editPetModal"
                                                    onclick="loadModal()"
                                            >
                                                Edit
                                            </button>
                                        </a></div>
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



    loadProfile();
    loadAllPets();

});

let pet;

function loadModal() {
    /*$('#editPetImagePreview').attr('src', '../' + pet.petImage);
    $('#editPetName').text(pet.petName);
    $('#editPetSpecies').text(pet.species);
    $('#editPetBreed').text(pet.breed);
    $('#editPetAge').text(pet.age);
    $('#editPetDob').text(pet.birthDate);*/

    $('#editPetImagePreview').attr('src', '../' + "asdasd");

}


