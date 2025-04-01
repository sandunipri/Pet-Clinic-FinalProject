$(document).ready(function () {
    $('#logOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });

    function allUsers(){
        $.ajax({
            url: 'http://localhost:8080/api/v1/admin/getAllUsers',
            type: 'GET',
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            success: function (response) {

                let users = response.data;
                let tableBody = $('#user_table');
                tableBody.empty();
                users.forEach(user => {
                    console.log(user.petList);
                    tableBody.append(`
                        <tr>
                            <td>
                            <img src="../${user.profileImage}"
                                         class="user-avatar me-2">
                                         </td>
                            <td>${user.email}</td>
                            <td>${user.name}</td>
                            <td>${user.address}</td>
                            <td>${user.telNo}</td>
                            <td>${user.petCount}</td>
                            <td>
                             <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-primary me-1">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                    
                             </td>
                        </tr>
                    `);
                });
            },
            error: function (error) {
                console.error('Error fetching users:', error);
            }
        });
    }
    allUsers();


});