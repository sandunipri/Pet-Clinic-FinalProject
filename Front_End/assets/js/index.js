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
});


