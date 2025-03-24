$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });


});