$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    // load profile image for the navigation bar
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    $.ajax({
        url: "http://localhost:8080/api/v1/user/getProfile",
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            $("#profileImage").attr("src", "../" + response.data.profileImage);
            $('#userName').text(response.name);
        },
        error: function (error) {
            console.log(error);
        }
    });

});




