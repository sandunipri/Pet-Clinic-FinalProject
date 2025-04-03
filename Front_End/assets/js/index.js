/*
function openPopup(popupId, overlayId) {
    document.getElementById(popupId).classList.add("popup-active");
    document.getElementById(overlayId).classList.add("popup-overlay-active");
}

function closePopup(popupId, overlayId) {
    document.getElementById(popupId).classList.remove("popup-active");
    document.getElementById(overlayId).classList.remove("popup-overlay-active");
}

document.getElementById("loginOpenPopup").addEventListener("click", function () {
    openPopup("popup", "loginPopupOverlay");
});

document.getElementById("registerPopup").addEventListener("click", function () {
    closePopup("popup", "loginPopupOverlay");
    openPopup("regPopup", "regPopupOverlay");
});

document.querySelectorAll('.popup-overlay').forEach(overlay => {
    overlay.addEventListener('click', function () {
        closePopup('popup', 'loginPopupOverlay');
        closePopup('regPopup', 'regPopupOverlay');
    });
});
*/

$(document).ready(function () {

    function callAgainToken(token) {
        if (token !== null) {
            $.ajax({
                url: 'http://localhost:8080/api/v1/user/logAgain',
                type: 'GET',
                headers: {"Authorization": "Bearer " + token},
                success: function (res) {
                    if (res.data === 'ADMIN') {
                        window.location.href = "admin.html";
                    }else {
                        window.location.href = 'client.html';
                    }
                },
                error: function (error) {
                    alert("session expired please login again");
                }
            })
        }
    }

    $('#loginBtn').click(function () {
        const token = localStorage.getItem('token')
        console.log("Token:", token);
        if (token !== null) {
            callAgainToken(token)
            return
        }
        window.location.href = 'index.html';
    });

    function getAllVetsDisplay() {
        console.log("sasfafafasf")

        let token = localStorage.getItem('token');
        console.log("getAllVetsDispaly");

        $.ajax({

            url: "http://localhost:8080/api/v1/veterinarian/getAll",
            type: 'GET',
            headers: {
                "Authorization": 'Bearer ' + token
            },

            success: function (response) {
                const fullName = `${vet.title}. ${vet.firstName} ${vet.lastName}`;

                for (let i = 0; i < response.data.length; i++) {
                    $('#vetLists').append(`
                <div class="row gy-4">
                         <div id="vetLists"
                            class="col-lg-3 col-md-6 d-flex align-items-stretch"
                            data-aos="fade-up"
                            data-aos-delay="100">
                       <div class="team-member">
                        <div class="member-img">
                            <img
                                    src="../${response.data[i].profileImage}"
                                    class="img-fluid"
                                    alt=""
                            />
                            <div class="social">
                                <a href=""><i class="bi bi-twitter-x"></i></a>
                                <a href=""><i class="bi bi-facebook"></i></a>
                                <a href=""><i class="bi bi-instagram"></i></a>
                                <a href=""><i class="bi bi-linkedin"></i></a>
                            </div>
                        </div>
                        <div class="member-info">
                            <h4>${fullName}</h4>
                            <span>Chief Medical Officer</span>
                        </div>
                    </div>
                </div>
                    `)
                }
            },
            error: function (error) {
                console.error("Error loading veterinarians:", error);
                $('#vetLists').html(
                    '<div class="col-12 text-center py-5"><p>Error loading veterinarians. Please try again.</p></div>'
                );

            }
        });
    }

    getAllVetsDisplay();
});


