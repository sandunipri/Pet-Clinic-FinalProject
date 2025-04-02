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
            petName: $(this).data("petname"),
            species: $(this).data("pettype"),
            breed: $(this).data("petbreed"),
            age: $(this).data("petage"),
            birthDate: $(this).data("petbod")


        };
        console.log(pet)
        viewPetProfile(pet);

    });

    $('#deletePet').click(function () {
        console.log("Delete Pet button clicked");
        let petId = $('#petId').val();
        console.log("Pet ID:", petId);

        $.ajax({
            url: "http://localhost:8080/api/v1/pet/deletePet/" + petId,
            type: "DELETE",
            success: function (data) {
                alert("Pet Deleted Success");
                loadAllPets();
            },
            error: function (data) {
                alert("Pet Deleted Failed");
            }
        });
    });

    loadProfile();
    loadAllPets();

});

function viewPetProfile(pet) {
    console.log("viewPetProfile function called");
    $('#editPetImagePreview').attr('src', '../' + pet.petImage);
    $('#petName').val(pet.petName);
    $('#species').val(pet.species);
    $('#breed').val(pet.breed);
    $('#age').val(pet.age);
    $('#birthDate').val(pet.birthDate);

    $('#editPetModal').modal('show');
}


