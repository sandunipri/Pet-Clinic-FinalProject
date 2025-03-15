
$('#LogOutBtn').click(function () {
    localStorage.removeItem('token');
    window.location.href = 'index.html';
});