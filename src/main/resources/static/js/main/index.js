var page = 0

$(document).ready(function () {
    getBeginningLessons()
    getPageWithFilter(page)
})

function getPageWithFilter(page) {
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
                if (i === 0) content += `<div class="row mt-0 gy-4">`
                else if (i % 2 === 0) content += `</div><div class="row mt-0 gy-4">`
                content += addBlock(courses.content[i])
                if (i === courses.content.length - 1) {
                    content += `</div>`
                    $("#courses").html(content)

                    $('#pagination_container').empty();
                    if (courses.totalPages > 1) updatePagination(page, courses.totalPages, 'pagination_container')
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
            console.log(objects)
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
                    cell2.innerHTML = `<center><a href="${object.link}"><i class="fa-solid fa-link"></i></a></center>`;
                }
            }
        },
        complete: function (xhr, status) {
            hideLoader("beginning-lessons")
        }
    })
}