var fullContextPath = 'http://slj.demodev.cc:7657/edu-tracker/student/';

$(document).ready(function () {
    if(window.location.href.includes("error")){
        $("#password-eye").css("border-color", "#ff0000")
        $("#password").css("border-color", "#ff0000")
        $("#username").css("border-color", "#ff0000")
        addText($("#password-block"), "Електрона адреса або пароль не коректні")
    }
})

function addText(input, message) {
    message = translateError(message)
    var icon = $('<p class="text-for-validating" style="color: #ff0000;">' + message + '</p>')
    icon.tooltip({
        content: message,
        position: {my: "left+15 center", at: "right center"}
    })
    input.after(icon);
    input.css("border-color", "#ff0000")
}
function translateError(key) {
    return key
        .replace('Please fill in the field', 'Заповніть поле')
        .replace('The number should be greater than', 'Число повинно бути більше, ніж')
        .replace('The number should be less than', 'Число повинно бути менше, ніж')
        .replace('The field should have fewer than', 'Поле повинно містити менше ніж')
        .replace('characters and more than', 'і більше ніж')
        .replace('ones', 'символів')
        .replace('characters', 'символів')
        .replace('Invalid email format', 'Невірний формат електронної пошти')
        .replace('Invalid phone format', 'Невірний формат телефону')
        .replace('File extension not valid', 'Тип файлу повиннен бути .jpeg, .png, .jpg')
        .replace('The telegram already exists!', 'Телеграм уже існує')
        .replace('The email already exists!', 'E-mail уже існує')
        .replace('The phone already exists!', 'Телефон уже існує')
        .replace('Passwords do not match!', ' Паролі не співпадають')
}
function showLoader(blockId) {
    $("#" + blockId).block({
        message: `
            <div class="d-flex justify-content-center">
                <p class="me-2 mb-0">Please wait...</p>
                <div class="sk-wave sk-primary m-0">
                    <div class="sk-rect sk-wave-rect"></div>
                    <div class="sk-rect sk-wave-rect"></div>
                    <div class="sk-rect sk-wave-rect"></div>
                    <div class="sk-rect sk-wave-rect"></div>
                    <div class="sk-rect sk-wave-rect"></div>
                </div>
            </div>`,
        css: {
            backgroundColor: "transparent",
            border: "0"
        },
        overlayCSS: {
            backgroundColor: "#fff",
            opacity: 0.8
        }
    });
}

function hideLoader(blockId) {
    $("#" + blockId).unblock();
}

function cleanInputs() {
    $('.text-for-validating').remove()
    var elements = document.querySelectorAll('input, select, textarea, button, .ql-editor,form');
    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];
        element.style.borderColor = '';
    }
    var select2Selects = document.querySelectorAll('.select2-selection');
    for (var i = 0; i < select2Selects.length; i++) {
        var select2Select = select2Selects[i];
        select2Select.style.borderColor = '';
    }
    $("#goal").css("border", "")
}
var countError = 0;
function validDataFromResponse(errors) {
    cleanInputs()
    for (var fieldName in errors) {
        if (errors.hasOwnProperty(fieldName)) {
            var errorMessage = errors[fieldName];
            scrollToElement($('#' + fieldName.toString()));
            addText($('#' + fieldName.toString()), errorMessage)
            $('#' + fieldName.toString()).css("border", "1px solid #ff0000")
        }
    }
    countError = 0
}

function scrollToElement($element) {
    if (countError !== 0) return
    countError++
    if ($element.length > 0) {
        var windowHeight = $(window).height();
        var targetOffset = $element.offset().top - windowHeight / 4;

        $('html, body').animate({
            scrollTop: targetOffset
        }, 100);
    }
}