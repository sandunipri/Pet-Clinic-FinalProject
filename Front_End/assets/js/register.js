$(document).ready(function () {
    $('#regBtn').click(function () {
        let name = $('#name').val().trim();
        let email = $('#email').val().trim();
        let password = $('#password').val().trim();

        // Proper regex definitions
        const emailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        // Remove old error messages
        $('.error-msg').remove();

        let isValid = true;

        // Name validation
        if (name === "") {
            $('#name').after('<span class="error-msg" style="color:red;font-size:12px;">Name is required</span>');
            isValid = false;
        }

        // Email validation
        if (email === "" || !emailRegex.test(email)) {
            $('#email').after('<span class="error-msg" style="color:red;font-size:12px;">Valid Gmail address is required</span>');
            isValid = false;
        }

        // Password validation
        if (!passwordRegex.test(password)) {
            $('#password').after('<span class="error-msg" style="color:red;font-size:12px;">Password must be at least 8 characters, include uppercase, lowercase, number, and special character</span>');
            isValid = false;
        }

        // Stop if validation fails
        if (!isValid) {
            return;
        }

        // Prepare form and send AJAX request
        let formData = new FormData($('#registerForm')[0]);

        $.ajax({
            url: "http://localhost:8080/api/v1/client/register",
            type: "POST",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                Swal.fire({
                    title: "Success!",
                    icon: "success",
                    text: "Register Success!"
                }).then(() => {
                    window.location.href = 'index.html';
                });
            },
            error: function (data) {
                Swal.fire({
                    title: "Error",
                    icon: "error",
                    text: "Register Failed"
                });
            }
        });
    });

    $('input').on('input', function () {
        $(this).next('.error-msg').remove();
    });

});
