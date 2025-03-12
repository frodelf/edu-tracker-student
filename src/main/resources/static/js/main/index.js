var page = 0
var pageForTask= 0
$(document).ready(function () {
    getBeginningLessons()
    getPageWithFilterForCourse(page)
    getPageWithFilterForTask(pageForTask)
    forSelect2WithSearchAndPageable('#tasks-for-filter', contextPath+'student-task/get-all-task-for-select')
    forSelect2WithSearchAndPageable('#courses-for-filter', contextPath+'student-task/get-all-course-for-select')
    forSelect2('#statuses-for-filter', contextPath+'enum/get-task-status')
})
function sendMain(lessonId, url){
    window.open(url, '_blank')
    $.ajax({
        url: contextPath + 'present/send',
        type: 'POST',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: {
            studentId: studentAuth.id,
            lessonId: lessonId
        },
        success: function (request) {
        },
    })
}
function getPageWithFilterForCourse(page) {
    showLoader('courses')
    this.page = page
    $.ajax({
        type: "Get",
        url: contextPath + 'course/get-all',
        data: {
            page: page,
            pageSize: pageSize
        },
        success: function (courses) {
            var content = ``
            for (var i = 0; i < courses.content.length; i++) {
                if (i === 0) content += `<div class="row mt-0">`
                else if (i % 2 === 0) content += `</div><div class="row mt-0 gy-4">`
                content += addBlock(courses.content[i])
                if (i === courses.content.length - 1) {
                    content += `</div>`
                    $("#courses").html(content)

                    $('#pagination_container').empty();
                    if (courses.totalPages > 1) updatePagination(page, courses.totalPages, 'pagination_container', 'getPageWithFilterForCourse')
                    return
                }
            }
        },
        complete: function (xhr, status) {
            hideLoader("courses")
        }
    })
}

function addBlock(course) {
    return `
        <div class="col-lg-6 col-md-12">
            <div class="card">
                <div class="card-content">
                    <div class="row" style="width: 96%; margin-left: 2%">
                        <div class="col-9">
                            <p class="font-weight-bond fs-3">${course.name}</p>
                            <br/>
                            Ціль курсу: ${course.goal}
                        </div>
                        <div class="col-3" style="padding-right: 0">
                            <img src="${course.image}" width="90%" height="90px" style="margin-top: 10px" class="float-end">
                        </div>
                    </div>
                </div>
                <div class="card-footer mt-4 mb-2" style="padding: 0">
                    <a href="${contextPath}course/${course.id}" class="btn btn-outline-secondary float-end" style="margin-right: 10px"><i class="fa-regular fa-eye"></i></a>
                </div>
            </div>
        </div>
    `
}



function getBeginningLessons() {
    showLoader("beginning-lessons")
    $.ajax({
        type: "Get",
        url: contextPath + 'lesson/get-beginning',
        success: function (objects) {
            var table = document.getElementById("lesson-table");
            var tbody = table.querySelector("tbody");
            $('#lesson-table tbody').empty();
            if($("#message-about-empty"))$("#message-about-empty").remove()
            if (objects.length == 0) {
                table.insertAdjacentHTML('afterend', '<center><h1 id="message-about-empty">Немає занять в даний момент!</h1></center>')
            }
            else {
                for (var object of objects) {
                    var newRow = tbody.insertRow();
                    newRow.id='lesson'+object.id
                    var cell0 = newRow.insertCell(0);
                    cell0.innerHTML = `${object.professorName}`;

                    var cell1 = newRow.insertCell(1);
                    if (object.course) {
                        const entries = Object.entries(object.course);
                        const courseLinks = entries.map(([key, value]) => {
                            return `<a href="${contextPath}course/${key}">${value}</a>`
                        })
                        courses = courseLinks.join(', ')
                    }
                    cell1.innerHTML = courses


                    var cell2 = newRow.insertCell(2);
                    cell2.innerHTML = `<center><a href="javascript:void()" onclick="sendMain(${object.id}, '${object.link}')"><i class="fa-solid fa-link"></i></a></center>`;
                }
            }
        },
        complete: function (xhr, status) {
            hideLoader("beginning-lessons")
        }
    })
}

function getPageWithFilterForTask(page) {
    showLoader('taskTable')
    this.pageForTask = page
    var filterElements = $('.for-filter');
    $.ajax({
        type: "Get",
        url: contextPath + 'student-task/get-all',
        data: {
            pageForTask: page,
            pageSize: pageSize,
            taskId: filterElements[0].value,
            courseId: filterElements[1].value,
            status: filterElements[2].value,
        },
        success: function (objects) {
            var table = document.getElementById("taskTable");
            var tbody = table.querySelector("tbody");
            $('#taskTable tbody').empty();
            if($("#message-about-empty-for-tasks"))$("#message-about-empty-for-tasks").remove()
            if(objects.content.length == 0){
                table.insertAdjacentHTML('afterend', '<center><h1 id="message-about-empty-for-tasks">Немає даних для відображення</h1></center>')
                $('#pagination_container_for_task').empty()
                return
            }
            for (var object of objects.content) {
                var newRow = tbody.insertRow();
                var cell0 = newRow.insertCell(0);
                cell0.innerHTML = `<a href="javascript:void(0)" onclick="downloadFileFrom('${object.taskFile}')">${object.taskName}`;

                var cell1 = newRow.insertCell(1);
                if (object.course) {
                    const entries = Object.entries(object.course);
                    const courseLinks = entries.map(([key, value]) => {
                        return `<a href="${contextPath}course/${key}">${value}</a>`
                    })
                    courses = courseLinks.join(', ')
                }
                cell1.innerHTML=courses

                var cell2 = newRow.insertCell(2);
                if (object.studentsTaskStatus == 'IN_PROCESS') {
                    cell2.innerHTML = `<center><span class="badge bg-label-danger">В процесі</span></center>`
                } else if(object.studentsTaskStatus == 'GRANTED') {
                    cell2.innerHTML = `<center><span class="badge bg-label-info">Здано</span></center>`
                }else if(object.studentsTaskStatus == 'OVERDUE') {
                    cell2.innerHTML = `<center><span class="badge bg-label-warning">Прострочено</span></center>`
                } else {
                    cell2.innerHTML = `<center><span class="badge bg-label-success">Оцінено</span></center>`
                }

                var cell3 = newRow.insertCell(3);
                if (object.studentsTaskStatus == 'IN_PROCESS') {
                    cell3.innerHTML = `<input type="file" class="form-control" id="file${object.id}" onchange="sendWork(${object.id})">`
                } else if(object.studentsTaskStatus == 'GRANTED') {
                    cell3.innerHTML = object.taskFile?
                                    `<center>
                                        <button class="btn" onclick="downloadFileFrom('${object.taskFile}')"><i class="fa-solid fa-arrow-down"></i></button>
                                        <button class="btn" style="padding: 7px"><i class="fa-solid fa-ban" onclick="cancelMark('${object.id}')"></i></button>
                                    </center>`
                                    :
                                    `<center>
                                        <button class="btn" style="padding: 7px"><i class="fa-solid fa-ban" onclick="cancelMark('${object.id}')"></i></button>
                                    </center>`
                }else if(object.studentsTaskStatus == 'OVERDUE') {
                    cell3.innerHTML = ``
                } else {
                    cell3.innerHTML = object.taskFile?
                        `<center><a href="javascript:void(0)" onclick="downloadFileFrom('${object.taskFile}')">${object.mark}</a></center>`
                        :
                        `<center>${object.mark}</center>`
                }
            }
            $('#pagination_container_for_task').empty();
            if (objects.totalPages > 1) updatePagination(page, objects.totalPages, 'pagination_container_for_task', 'getPageWithFilterForTask')
        },
        complete: function (xhr, status) {
            hideLoader("taskTable")
        }
    })
}
function sendWork(id) {
    var fileInput = $("#file" + id)[0];
    var file = fileInput.files[0];

    var formData = new FormData();
    formData.append("id", id);
    formData.append("file", file);

    $.ajax({
        url: contextPath + 'student-task/send-work',
        type: 'PUT',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            showSuccessToast("Роботу здано");
            getPageWithFilterForTask(pageForTask);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Помилка при відправці роботи:', textStatus, errorThrown);
        }
    });
}

function cancelMark(studentTaskId){
    $.ajax({
        url: contextPath + 'student-task/cancel-work',
        type: 'PUT',
        headers: {'X-XSRF-TOKEN': csrf_token},
        data: {
            id: studentTaskId
        },
        success: function (request) {
            showSuccessToast("Роботу скасовано")
            getPageWithFilterForTask(pageForTask)
        },
    })
}

function downloadFileFrom(fileName) {
    $.ajax({
        type: "GET",
        url: contextPath+"minio/download",
        xhrFields: {
            responseType: 'blob'
        },
        data: fileName!='null' ? { fileName: fileName } : {},
        success: function (data) {
            var blob = new Blob([data], {type: 'application/octet-stream'});
            var filename = file.replace(/^[^.]*\./, '')

            if (window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, filename);
            } else {
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = filename;
                link.click();
            }
        },
        error: function () {
            console.log("Помилка завантаження файлу");
        }
    });
}