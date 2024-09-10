$(document).ready(function () {
    if(course.image){
        $.ajax({
            type: "Get",
            url: contextPath + 'minio/get-image',
            data: {
                imageName: course.image
            },
            success: function (url) {
                $("#course-image").attr('src', url)
            }
        })
    }
    $("#course-goal").html(course.goal)

    $.ajax({
        url: contextPath + 'course/statistic',
        type: 'Get',
        data: {
            id: course.id
        },
        success: function (statistics) {
            addBlock(statistics)
        },
    })
})

function addBlock(statistics){
    var statistic = $('#course-statistic');
    if (statistic.html()) statistic.html('')
    if(statistic.status){
        statistic.html(`
    <div class="row">
        <center><h2>Недостатньо даних для відображення</h2></center>
    </div>`)
        return
    }

    statistic.html(`
    <div class="row">
        <div class="col-lg-3 col-md-6 col-sm-12">
            <div class="card drag-item  mb-lg-0 mb-6">
                <div class="card-body text-center">
                <h2>
                    <i class="fa-solid fa-users" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>${statistics.mark || ''}</h4>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-12">
            <div class="card drag-item  mb-lg-0 mb-6">
                <div class="card-body text-center">
                <h2>
                    <i class="fa-solid fa-users" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>Завдання</h4>
                <h5>
                    <div style="display: flex; justify-content: center; align-items: center">
                        <div>${statistics.allTasks || ''}  (</div>
                        <div style="color: green">${statistics.doneTasks || ''}</div>
                        <div>-</div>
                        <div style="color: red">${statistics.notDoneTasks || ''}</div>
                        <div>)</div>
                    </div>
                </h5>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-12">
            <div class="card drag-item  mb-lg-0 mb-6">
                <div class="card-body text-center">
                <h2>
                    <i class="fa-solid fa-users" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>Відвідані заняття</h4>
                <h5>${statistics.lessons || ''}</h5>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-12">
            <div class="card drag-item  mb-lg-0 mb-6">
                <div class="card-body text-center">
                <h2>
                    <i class="fa-solid fa-users" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>Оцінка</h4>
                <h5>${statistics.literatures || ''}</h5>
                </div>
            </div>
        </div>
    </div>
    `)
}