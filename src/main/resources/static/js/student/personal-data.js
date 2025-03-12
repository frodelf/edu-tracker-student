$(document).ready(function () {
    showLoader("content-form")
    $('#avatar-img').html(nameForAvatar)
    $.ajax({
        url: contextPath + 'get-personal-data',
        method: 'GET',
        success: function (student) {
            $('#phone').val(student.phone)
            $('#lastName').val(student.lastName)
            $('#name').val(student.name)
            $('#telegram').val(student.telegram)
            $('#middleName').val(student.middleName)
            $('#email').val(student.email)
            if (student.image) {
                $('.avatar-preview').html(`
                    <img src="${student.image}" width=250px height=250px style="border-radius: 50%">
                `)
            }
        },
        complete: function (xhr, status) {
            hideLoader("content-form")
        }
    })
    document.getElementById('image').addEventListener('change', function (event) {
        const file = event.target.files[0]
        const validExtensions = ['image/jpeg', 'image/png'];

        if (!validExtensions.includes(file.type)) {
            showErrorToast("Файл повинен мати формат jpg, jpeg або png")
            document.getElementById('image').value = "";
            return
        }

        if (file) {
            $('.avatar-preview').html(`<img id="avatar-img" src=""  style="border-radius: 50%">`)
            const reader = new FileReader()
            reader.onload = function (e) {
                document.getElementById('avatar-img').src = e.target.result
            }
            reader.readAsDataURL(file)
        }
    })
})

function updatePersonalData() {
    showLoader("content-form")
    let formData = new FormData()
    formData.append('middleName', $("#middleName").val())
    formData.append('name', $("#name").val())
    formData.append('lastName', $("#lastName").val())
    if ($("#image").val()) formData.append('image', $("#image")[0].files[0])
    formData.append('phone', $("#phone").val())
    formData.append('email', $("#email").val())
    formData.append('telegram', $("#telegram").val())

    $.ajax({
        url: contextPath + 'update-personal-data',
        type: 'PUT',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: formData,
        contentType: false,
        processData: false,
        success: function () {
            cleanInputs()
            showToastForSave()
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                cleanInputs()
                validDataFromResponse(xhr.responseJSON)
            } else {
                console.error('Помилка відправки файлів на сервер:', error);
            }
        },
        complete: function (xhr, status) {
            hideLoader("content-form")
        }
    })
}
function changePassword(){
    if($('#ModalForChangePassword').html())$('#ModalForChangePassword').remove()
    var modalBlock = document.createElement('div');
    modalBlock.innerHTML = `
    <div class="modal fade" id="ModalForChangePassword" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Змінити пароль</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Новий пароль
                        <input id="password" class="mb-4 form-control" type="password">
                        Повтор пароля
                        <input id="password-rep" class="form-control" type="password">
                        <div class="row">
                            <center>
                                <button class="btn btn-outline-primary mt-3" onclick="changePasswordSubmit($('#password').val(), $('#password-rep').val())">Змінити</button>
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
    document.body.appendChild(modalBlock);
    $('#ModalForChangePassword').modal('show');
}

function changePasswordSubmit(password1, password2){
    if(password1 != password2){
        showErrorToast("Паролі не співпадають")
        return
    }
    showLoader("ModalForChangePassword")

    $.ajax({
        type: "post",
        url: contextPath + 'change-password?password='+password1,
        headers: {'X-XSRF-TOKEN': csrf_token},
        contentType: false,
        processData: false,
        success: function () {
            showSuccessToast("Пароль змінений")
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                showErrorToast("Пароль має складатись з 8 символів")
            } else {
                console.error('Помилка відправки файлів на сервер:', error);
            }
        },
        complete: function (xhr, status) {
            hideLoader("ModalForChangePassword")
        }
    })
}