$(document).ready(function () {
    $('#loginBtn').click(function () {

        let Email = $('#exampleInputEmail1').val().trim();
        let Password = $('#exampleInputPassword1').val().trim();

        //regex apply for filed
        const emailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        //delete error msg
        $('.error-msg').remove();

        let isValid = true;

        // Email validation
        if (Email === "" || !emailRegex.test(Email)) {
            $('#exampleInputEmail1').after('<span class="error-msg" style="color:red;font-size:12px;">Valid Gmail address is required</span>');
            isValid = false;
        }

        // Password validation
        if (!passwordRegex.test(Password)) {
            $('#exampleInputPassword1').after('<span class="error-msg" style="color:red;font-size:12px;">Password is incorrect </span>');
            isValid = false;
        }

        // Stop if validation fails
        if (!isValid) {
            return;
        }

        // Prepare form and send AJAX request
        $.ajax({
            url: "http://localhost:8080/api/v1/auth/authenticate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "email": Email,
                "password": Password,
                "name":name

            }),
            success: function (res) {
                console.log("Response:", res.data); // Logs the entire response

                let user = res.data.userDTO
                let token = res.data.token;

                console.log("User Object:", user); // Logs user object
                console.log("Token:", token); // Logs user role

                if (user && token) {
                    Swal.fire({
                        icon: 'success',
                        title: `Welcome, ${user.name || "User"}!`,
                        text: `Successfully logged in as ${user.role || "Unknown"}`,
                        timer: 2000,
                        showConfirmButton: false
                    }).then(() => {
                        window.localStorage.setItem("token", token);
                        window.localStorage.setItem("user", JSON.stringify(user));

                        if (user.role === "USER") {
                            window.location.href = 'client.html';
                        } else if (user.role === "ADMIN") {
                            window.location.href = 'admin.html';
                        } else if (user.role === "VET") {
                            window.location.href = 'vet.html';
                        }
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Invalid Login',
                        text: 'Invalid login response. Please check the again your  details.'
                    });
                    console.error("Unexpected API response structure:", res);
                }

            },
            error: function (res) {
                console.log(res);
                Swal.fire({
                    icon: 'error',
                    title: 'Login Failed',
                    text: 'Please check your credentials and try again.'
                });
            }
        });
    });


    $('input').on('input', function () {
        $(this).next('.error-msg').remove();
    });

});