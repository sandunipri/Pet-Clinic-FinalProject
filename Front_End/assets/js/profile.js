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
            $("#photoPreview").attr("src", "../" + response.data.profileImage);
            $('#Useremail').text(response.data.email);
            $('#userName').text(response.data.name);
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

$('#updateProfileBtn').click(function () {
    let formData = new FormData($('#updateForm')[0]);

    console.log("updateProfileBtn");

    let  token = localStorage.getItem('token');
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
            alert("Profile Updated Successfully");
            loadProfile();
        },
        error: function (response) {
            alert("Error");
        }
    })
});

$('#deleteBtn').click(function () {
    let token = localStorage.getItem('token');
    $.ajax({
        url: "http://localhost:8080/api/v1/user/delete",
        type: "DELETE",
        contentType: "application/json",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            alert("Profile Deleted Successfully");
            localStorage.removeItem('token');
            window.location.href = 'index.html';
        },
        error: function (response) {
            alert("Error");
        }
    })
});

loadProfile();
});