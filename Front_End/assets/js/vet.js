$(document).ready(function () {
    $('#saveVet').click(function () {
        let token = localStorage.getItem('token');
        let formData = new FormData($('#addVetForm')[0]);
        console.log("saveVet");

        console.log(token)
        console.log(formData)

        $.ajax({
            url: "http://localhost:8080/api/v1/veterinarian/save",
            type: "POST",
            data: formData,
            headers: {
                "Authorization": 'Bearer ' + token
            },
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                Swal.fire({
                    title: "Success!",
                    icon: "success",
                    text: "Vet Added Successfully!"
                }).then(() => {
                    window.location.href = 'vet.html';
                });
            },
            error: function (data) {
                alert("Vet Adding Failed");
            }
        })
    })

    function getAllVetsDisplay() {
        let token = localStorage.getItem('token');
        console.log("getAllVetsDispaly");

        $.ajax({
            url: "http://localhost:8080/api/v1/veterinarian/getAll",
            type: 'GET',

            success: function (response) {

                const doctorSelect = $('#doctor');
                doctorSelect.empty().append('<option value="">Select Doctor</option>');

                for (let i = 0; i < response.data.length; i++) {

                    const vet = response.data[i];
                    const fullName = `${vet.title}. ${vet.firstName} ${vet.lastName}`;

                    doctorSelect.append(`<option value="${fullName}">${fullName}</option>`);

                    $('#docList').append(`
                        <div class="col-lg-3 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
                            <div class="team-member card h-100 border-0">
                                <div class="member-img position-relative overflow-hidden">
                                    <img id="profileImage" src="../${vet.profileImage}" class="img-fluid w-100" alt="Dr. Walter White" />
                                    <div class="social d-flex justify-content-center align-items-center py-2">
                                        <a href="#" class="mx-2 text-decoration-none" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                                        <a href="#" class="mx-2 text-decoration-none" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                                        <a href="#" class="mx-2 text-decoration-none" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                                        <a href="#" class="mx-2 text-decoration-none" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                                    </div>
                                </div>
                                <div class="member-info card-body text-center">
                                    <h4 id="name" class="card-title fw-bold">${fullName}</h4>
                                    <span>Chief Veterinary Officer</span>
                                    <p id="specialty" class="card-text mt-2 small" style="color: var(--text-light)">
                                    ${vet.specialty}
                                    </p>
                                    <button class="btn btn-outline-primary btn-sm mt-2 channel-btn" 
                                        data-doctorname="${fullName}">
                                        CHANNEL
                                    </button>
                                </div>
                            </div>
                        </div>
                    `)
                }
            },
            error: function (error) {
                console.error("Error loading veterinarians:", error);
                $('#docList').html(
                    '<div class="col-12 text-center py-5"><p>Error loading veterinarians. Please try again.</p></div>'
                );

            }
        });
    }
    // Handle channel button clicks
    function loadDoctors() {
        $(document).on('click', '.channel-btn', function(e) {
            e.preventDefault();
            const doctorName = $(this).data('doctorname');
            const appointmentSection = $('#appointment');

            // Set the selected doctor
            $('#doctor').val(doctorName).trigger('change');

            // Show and scroll to appointment form
            appointmentSection.css('display', 'block');
            $('html, body').animate({
                scrollTop: appointmentSection.offset().top - 20
            }, 500);

            // Focus on the first field
            $('#name').focus();
        });
    }

    function setupMobileNav() {
        $('.mobile-nav-toggle').click(function() {
            $('#navmenu').toggleClass('navmenu-active');
            $(this).toggleClass('bi-list bi-x');
        });
    }

    getAllVetsDisplay();
    loadDoctors();
    // handleAppointmentForm();
    setupMobileNav();

})
