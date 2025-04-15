$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });
// load to data for the profile
    function loadProfile() {
    let token = localStorage.getItem('token');

    $.ajax({
        url: "http://localhost:8080/api/v1/user/getProfile",
        type: "GET",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            //load side profile
            $('#userName').text(response.data.name);
            $('#CurrentEmail').text(response.data.email);
            
            $("#photoPreview").attr("src", "../" + response.data.profileImage);
            $('#Useremail').text(response.data.email);
            $('#fullname').text(response.data.name);
            $('#userGender').text(response.data.gender);
            $('#userNic').text(response.data.nic);
            $('#userAddress').text(response.data.address);
            $('#userTelNo').text (response.data.telNo);
            $('#phoneNo').text(response.data.emergencyContact);
            $('#userPhoneNo').text(response.data.emergencyContact);
            $('#userEmergencyContactName').text(response.data.emergencyContactName);


            //update profile ajax part save
            $("#currentProfilePic").attr("src", "../" + response.data.profileImage);
            $('#email').val(response.data.email);
            $('#name').val(response.data.name);
            $('#address').val(response.data.address);
            $('#telNo').val (response.data.telNo);
            $('#gender').val(response.data.gender);
            $('#nic').val(response.data.nic);
            $('#emergencyContact').val(response.data.emergencyContact);
            $('#emergencyContactName').val(response.data.emergencyContactName);
            $('#profilePic').val(response.data.profileImage);

        },
        error: function (response) {
            alert("Something went wrong");
            localStorage.removeItem('token');
            window.location.href = 'index.html';
        }
    })
}

    $('#updateProfileBtn').click(function (e) {
        e.preventDefault(); // prevent form submission if inside a form

        let name = $('#name').val().trim();
        let email = $('#email').val().trim();
        let address = $('#address').val().trim();
        let telNo = $('#telNo').val().trim();
        let gender = $('#gender').val().trim();
        let nic = $('#nic').val().trim();
        let emergencyContact = $('#emergencyContact').val().trim();
        let emergencyContactName = $('#emergencyContactName').val().trim();

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const phoneRegex = /^[0-9]{10}$/;

        let errorMessages = [];

        $('input, select').removeClass('input-error');

        if (!name) {
            errorMessages.push("Name is required.");
            $('#name').addClass('input-error');
        }

        if (!email || !emailRegex.test(email)) {
            errorMessages.push("Valid email is required.");
            $('#email').addClass('input-error');
        }

        if (!address) {
            errorMessages.push("Address is required.");
            $('#address').addClass('input-error');
        }

        if (!telNo || !phoneRegex.test(telNo)) {
            errorMessages.push("Valid phone number is required (10 digits).");
            $('#telNo').addClass('input-error');
        }

        if (!gender) {
            errorMessages.push("Gender is required.");
            $('#gender').addClass('input-error');
        }

        if (!nic) {
            errorMessages.push("NIC is required.");
            $('#nic').addClass('input-error');
        }

        if (!emergencyContact || !phoneRegex.test(emergencyContact)) {
            errorMessages.push("Valid emergency contact number is required.");
            $('#emergencyContact').addClass('input-error');
        }

        if (!emergencyContactName) {
            errorMessages.push("Emergency contact name is required.");
            $('#emergencyContactName').addClass('input-error');
        }

        if (errorMessages.length > 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Errors',
                html: `<ul style="text-align:left;">${errorMessages.map(e => `<li>${e}</li>`).join('')}</ul>`
            });
            return;
        }


        let formData = new FormData($('#updateForm')[0]);
        let token = localStorage.getItem('token');

        $.ajax({
            url: "http://localhost:8080/api/v1/user/updateProfile",
            type: "PUT",
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (response) {
                Swal.fire({
                    title: "Success!",
                    icon: "success",
                    text: "Profile Updated Successfully"
                }).then(() => {
                    loadProfile();
                    $('#editProfileModal').modal('hide');
                });
            },
            error: function (response) {
                Swal.fire({
                    title: "Error",
                    icon: "error",
                    text: "Something went wrong"
                });
            }
        });
    });

    $('#deleteBtn').click(function (e) {
        e.preventDefault();
        e.stopPropagation();

        return Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'Cancel'
        }).then((result) => {
            if (result.isConfirmed) {
                return deleteAccount();
            }
            return false;
        });
    });

    function deleteAccount() {
        let token = localStorage.getItem('token');
        return $.ajax({
            url: "http://localhost:8080/api/v1/user/delete",
            type: "DELETE",
            contentType: "application/json",
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).then(function(response) {
            return Swal.fire({
                title: "Deleted!",
                icon: "success",
                text: "Your account has been deleted."
            }).then(() => {
                localStorage.removeItem('token');
                window.location.href = 'index.html';
            });
        }).catch(function(xhr) {
            let errorMsg = xhr.responseJSON?.message || "Something went wrong";
            return Swal.fire({
                title: "Error",
                icon: "error",
                text: errorMsg
            });
        });
    }

loadProfile();
});