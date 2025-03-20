

    /*function fetchProfileData() {
    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        dataType: "application/json",
        success: function (response) {
            console.log("Response:", response);

            let user = response.data.userDTO

            if (!user.error) {
                document.querySelector(".card-title").innerHTML = `🧑‍💼 ${data.name}`;
                document.querySelector(".text-muted").textContent = data.role;
                document.querySelectorAll(".desc p")[0].innerHTML = `<strong>📍 Address:</strong> ${data.address}`;
                document.querySelectorAll(".desc p")[1].innerHTML = `<strong>📞 Tel:</strong> ${data.telNo}`;
                document.querySelectorAll(".desc p")[2].innerHTML = `<strong>✉️ Email:</strong> ${data.email}`;
            } else {
                document.querySelector(".desc").innerHTML = `<p class="text-danger">User not found.</p>`;
            }
        },
        error: function () {
            document.querySelector(".desc").innerHTML = `<p class="text-danger">Failed to load data.</p>`;
        }
    });
}*/

    const getAllUsers = () => {
        console.log("Get All Customers")
        $.ajax({
            url: "http://localhost:8080/api/v1/user/get",
            type: "GET",
            success: function (response) {
                console.log("Response:", response);

                // let user = response.data.userDTO

                let email = response.data.email
                console.log("email" ,email);

                // let username = user.name
                // console.log("User name:", username);

               /* let role = response.data.userDTO.role
                console.log("User Role:", role);

                let address = response.data.userDTO.address
                console.log("User Address:", address);

                let telNo = response.data.userDTO.telNo
                console.log("User Tel:", telNo);*/


                if (!user.error) {
                    document.querySelector(".card-title").innerHTML = `🧑‍💼 ${username}`;
                    document.querySelector(".text-muted").textContent = role;
                    document.querySelectorAll(".desc p")[0].innerHTML = `<strong>📍 Address:</strong> ${address}`;
                    document.querySelectorAll(".desc p")[1].innerHTML = `<strong>📞 Tel:</strong> ${telNo}`;
                    document.querySelectorAll(".desc p")[2].innerHTML = `<strong>✉️ Email:</strong> ${email}`;
                } else {
                    document.querySelector(".desc").innerHTML = `<p class="text-danger">User not found.</p>`;
                }

            },
            error: function () {
                document.querySelector(".desc").innerHTML = `<p class="text-danger">Failed to load data.</p>`;
            }

        })

    }
    getAllUsers();


        $('#LogOutBtn').click(function () {
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    });
