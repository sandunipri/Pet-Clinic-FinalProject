$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    // load to data for the profile
 /*   function loadProfile() {
        let token = localStorage.getItem('token');

        $.ajax({
            url: "http://localhost:8080/api/v1/user/getProfile",
            type: "GET",
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (response) {
                // $("#userProfileImage").attr("src", "../assets/images/img.png"+response.data.profileImage);
                $('#adminName').text(response.data.name);
                //set email to input field
                $('#email').val(response.data.email);
                $('#name').val(response.data.name);
                $('#adminAddress').val(response.data.address);
                $('#telNo').val(response.data.telNo);
            },
            error: function (response) {
                alert("Something went wrong");
                localStorage.removeItem('token');
                window.location.href = 'index.html';
            }
        })
    }

    loadProfile();*/
});