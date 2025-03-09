
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

    // Close popups when clicking outside the popup (on the overlay)
    document.querySelectorAll(".popup-overlay").forEach((overlay) => {
    overlay.addEventListener("click", function () {
        closePopup("popup", "loginPopupOverlay");
        closePopup("regPopup", "regPopupOverlay");
    });
});

    /*    // Close popups when clicking outside the popup (on the body)
    document.addEventListener("click", function (event) {
    let popup = document.querySelector(".popup-active");
    if (popup && !popup.contains(event.target) && !event.target.closest(".btnVideo")) {
    closePopup("popup", "loginPopupOverlay");
    closePopup("regPopup", "regPopupOverlay");
}
});*/

