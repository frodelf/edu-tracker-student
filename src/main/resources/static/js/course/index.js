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
                    <i class="fa-solid fa-user-graduate" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>Оцінка</h4>
                <h5>${statistics.mark || ''}</h5>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-12">
            <div class="card drag-item  mb-lg-0 mb-6">
                <div class="card-body text-center">
                <h2>
                    <i class="fa-solid fa-list-check" style="color: #B197FC;"></i>                                    
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
                    <i class="fa-solid fa-person-chalkboard" style="color: #B197FC;"></i>                                    
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
                    <i class="fa-solid fa-book" style="color: #B197FC;"></i>                                    
                </h2>
                <h4>Література</h4>
                <h5><a href="javascript:void(0)" onclick="getAllLiterature()">${statistics.literatures || ''}</a></h5>
                </div>
            </div>
        </div>
    </div>
    `)
}
function showAllLiterature(literatures){
    var tablesRow = ''
    for (const literature of literatures) {
        tablesRow+=`<tr><td><a href="${literature.link}" target="_blank">${literature.name}</a></td></tr>`
    }
    if($('#allLiterature').html())$('#allLiterature').remove()
    var modalBlock = document.createElement('div');
    modalBlock.innerHTML = `
    <div class="modal fade" id="allLiterature" tabindex="-1" aria-labelledby="modalScrollableTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalScrollableTitle">Literatures</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
        </button>
      </div>
      <div class="modal-body">
        <table class="table table-bordered table-hover table-striped linkedRow">
          ${tablesRow}
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
    `
    document.body.appendChild(modalBlock);
    $('#allLiterature').modal('show');
}

function getAllLiterature(){
    $.ajax({
        type: "get",
        url: contextPath + 'literature/get-all-by-course-id/' + course.id,
        success: function (literatures) {
            showAllLiterature(literatures)
        }
    })
}