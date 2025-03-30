$(document).ready(function () {
    /*
        $('#logOutBtn').click(function () {
            localStorage.removeItem('token');
            window.location.href = 'index.html';
        });
    */

    $('#addPet').click(function () {
        console.log("Add Pet button clicked");
        let token = localStorage.getItem('token');
        if (!token) {
            alert("Please log in to add a pet.");
            return;
        }

        let formData = new FormData($('#pet-registration-form')[0]);
        // Get the selected pet type
        const petType = document.querySelector('input[name="species"]:checked').value;
        console.log("Selected Pet Type:", petType);

        console.log(formData);

        $.ajax({
            url: "http://localhost:8080/api/v1/pet/save",
            type: "POST",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (data) {
                alert("Pet Added Success");
            },
            error: function (data) {
                alert("Pet Added Failed");
            }
        });
    })


    /*   document.getElementById('pet-registration-form').addEventListener('submit', function (e) {
           e.preventDefault();

           // Get form values
           const petType = document.querySelector('input[name="pet-type"]:checked').nextElementSibling.textContent;
           const petName = document.getElementById('name').value;

           // Show success message (in a real app, you would send this to your backend)
           alert(`Successfully registered ${petName} the ${petType}!`);

           // Reset form
           this.reset();

           // In a real application, you would redirect to the pet profile page
           // window.location.href = 'my-pets.html';
       });

   // Mobile Navigation Toggle
      document.addEventListener('DOMContentLoaded', function () {
          const mobileNavToggle = document.querySelector('.mobile-nav-toggle');
          const navmenu = document.querySelector('#navmenu');

          mobileNavToggle.addEventListener('click', function () {
              navmenu.classList.toggle('navmenu-active');
          });
      });*/

});