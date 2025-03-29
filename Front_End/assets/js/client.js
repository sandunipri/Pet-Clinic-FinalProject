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
    loadProfile();

});




