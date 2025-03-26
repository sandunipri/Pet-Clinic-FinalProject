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
                $("#profileImage").attr("src", "../assets/images/img.png"+response.data.profileImage);
                $('#email').val(response.data.email);
                $('#name').val(response.data.name);
                $('#address').val(response.data.address);
                $('#telNo').val(response.data.telNo);
            },
            error: function (response) {
                alert("Something went wrong");
                localStorage.removeItem('token');
                window.location.href = 'index.html';
            }
        })
    }

    $('#updateProfileBtn').click(function () {

        console.log("updateProfileBtn");

        let  token = localStorage.getItem('token');
        $.ajax({
            url: "http://localhost:8080/api/v1/user/updateProfile",
            type: "PUT",
            headers: {
                'Authorization': 'Bearer ' + token
            },
            contentType: "application/json",
            data: JSON.stringify({
                "email": $('#email').val(),
                "password": null,
                'name': $('#name').val(),
                "role":null,
                "address": $('#address').val(),
                "telNo": $('#telNo').val(),
                "profileImage":null
            }),
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




