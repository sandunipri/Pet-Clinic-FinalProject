$(document).ready(function () {
    $('#loginBtn').click(function () {
        let Email = $('#exampleInputEmail1').val();
        let Password = $('#exampleInputPassword1').val();

        $.ajax({
            url: "http://localhost:8080/api/v1/auth/authenticate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "email": Email,
                "password": Password,

            }),

                /*console.log(res);

                let user = res.data.user;
                console.log(user);

                if (res.data && res.data.user) {
                    let user = res.data.user;
                    if (user.role === "USER") {
                        alert('Successfully login User');
                        window.location.href = 'client.html';
                        window.localStorage.setItem("token", res.data.token);

                    } else if (user.role === "ADMIN") {
                        alert('Successfully login Admin');
                        window.localStorage.setItem("token", res.data.token);
                        window.location.href = 'admin.html';
                    }
                }
*/

            success: function (res) {
                console.log("Response:", res); // Logs the entire response

                let user = res.data.userDTO
                let token = res.token || res.data?.token;

                console.log("User Object:", user); // Logs user object
                console.log("User Role:",res.data.userDTO.role); // Logs user role

                if (user && token) {
                    alert(`Successfully logged in as ${user.role || "Unknown"}`);
                    window.localStorage.setItem("token", token);

                    if (user.role === "USER") {
                        window.location.href = 'client.html';
                    } else if (user.role === "ADMIN") {
                        window.location.href = 'admin.html';
                    } else {
                        alert("Unknown role. Please contact support.");
                    }
                } else {
                    alert("Invalid login response. Please check the console for details.");
                    console.error("Unexpected API response structure:", res);
                }
            },
            error: function (res) {
                console.log(res);
                alert('Login Failed');
            }
        });
    });
});