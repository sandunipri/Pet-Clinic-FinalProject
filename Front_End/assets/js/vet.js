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
            url: "http://localhost:8080/api/v1/veterinarian/getAllVeterinary",
            type: 'GET',


            success: function (response) {

                const doctorSelect = $('#doctor');
                const specialtySelect = $('#specialty-select');
                doctorSelect.empty().append('<option value="">Select Doctor</option>');
                specialtySelect.empty().append(`<option value="">Department</option>`);

                for (let i = 0; i < response.data.length; i++) {

                    const vet = response.data[i];
                    const fullName = `${vet.title}. ${vet.firstName} ${vet.lastName}`;

                    doctorSelect.append(`<option value="${vet.id}">${fullName}</option>`);
                    specialtySelect.append(`<option value="${vet.specialty.toLowerCase()}">${vet.specialty}</option>`);


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
                                    <p id="specialty" data-doctorspecialty="${vet.specialty}" class="card-text mt-2 small" style="color: var(--text-light)" >
                                    ${vet.specialty}
                                    </p>
                                    <button class="btn btn-outline-primary btn-sm mt-2 channel-btn" 
                                        data-doctorname="${vet.id}">
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

    function loadDoctors() {
        $(document).on('click', '.channel-btn', function(e) {
            e.preventDefault();

            let user = localStorage.getItem("user");
            let userJSON = JSON.parse(user)

            $('#userName').val(userJSON.name);
            $('#email').val(userJSON.email);

            const doctorName = $(this).data('doctorname');
            const doctorSpecialty = $(this).closest('.member-info').find('#specialty').data('doctorspecialty');
            console.log("Selected Doctor:", doctorName);
            console.log("Selected Specialty:", doctorSpecialty);
            const appointmentSection = $('#appointment');

            // Set the selected doctor
            $('#doctor').val(doctorName).trigger('change');
            // Set the selected specialty
            $('#specialty-select').val(doctorSpecialty.toLowerCase()).trigger('change');
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

    function loadVeProfile(){
        let token = localStorage.getItem('token');
        console.log("loadVeProfile");
        console.log(token)

        let vetId = sessionStorage.getItem('selectedVetId')
        console.log(vetId)

        $.ajax({
            url: `http://localhost:8080/api/v1/veterinarian/getVetProfile?id=${vetId}`,
            type: 'GET',
            headers: {
                "Authorization": 'Bearer ' + token
            },
            success: function (response) {
                console.log("response")
                $("#imagePreview").attr("src", "../" + response.data.profileImage);
                $("#vetName").text(response.data.title + ". " + response.data.firstName + " " + response.data.lastName);
                $("#licenseNo").text(response.data.licenseNo);
                $("#specialty").text(response.data.specialty);

                /* personal Information*/
                $("#vetTitle").text(response.data.title)
                $("#vetFullName").text(response.data.firstName + " " + response.data.lastName);
                $("#vetGender").text(response.data.gender);
                $("#vetSpecialty").text(response.data.specialty);
                $("#vetExperience").text(response.data.YOEeperience);

                /*contact information*/
                $("#vetEmail").text(response.data.email);
                $("#vetTelNo").text(response.data.phone);
                $("#vetAddress").text(response.data.address + " " + response.data.city);


                /*update model data set for the */
                $("#profilePreview").attr("src", "../" + response.data.profileImage);
                $("#fullName").val(response.data.firstName + " " + response.data.lastName);
                $("#title").val(response.data.title);
                $("#license").val(response.data.licenseNo)
                $("#email").val(response.data.email);
                $("#phone").val(response.data.phone);
                $("#address").val(response.data.address + " " + response.data.city);

                $("#experience").val(response.data.YOEeperience);








            },
            error: function (error) {
                console.error("Error loading veterinarian profile:", error);
            }
        });
    }

    getAllVetsDisplay();
    loadDoctors();
    setupMobileNav();
    loadVeProfile();

})
