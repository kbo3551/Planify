const Toast = plan.util.getToast();
$(document).ready(function () {
    init();
    eventListener();
});

function dataTableInit(isAdmin) {
    const columns = [
        { data: 'index', width: '10%'},
        { data: 'title', render: (data, type, row) => `<a href="#" onclick="viewDetail(${row.noticeId})">${data}</a>`, width: '40%'},
        { data: 'regName', width: '15%' },
        { data: 'regDt', render: formatDate, width: '25%' }
    ];

    // 관리자인 경우에만 수정/삭제 버튼 컬럼 추가
    if (isAdmin) {
        columns.push({
            data: null,
            width: '10%',
            render: (data, type, row) => `<div class="d-flex justify-content-center">
                                                     <button class="btn btn-sm btn-warning mr-1" onclick="editNotice(${row.noticeId})">수정</button>
                                                     <button class="btn btn-sm btn-danger" onclick="deleteNotice(${row.noticeId})">삭제</button>
                                                 </div>`
        });
    }

    // DataTable 초기화
    $('#noticeTable').DataTable({
        ajax: function (data, callback, settings) {
            plan.util.AJAX_Json('/api/notices', '', 'GET', 'json').done(function (response) {
                if (response.status === 200) {
                    var noticesWithIndex = response.data.map(function (item, index) {
                        item.index = index + 1;
                        return item;
                    });

                    callback({ data: noticesWithIndex });
                } else {
                    Swal.fire({
                        title: "Error",
                        text: response.message,
                        icon: "error"
                    });
                    callback({ data: [] });
                }
            }).fail(showError);
        },
        columns: columns,
        order: [[3, 'desc']],
        language: {
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            zeroRecords: "목록이 없습니다.",
            info: "총 _TOTAL_개의 목록 중 _START_에서 _END_까지 표시",
            infoEmpty: "",
            search: "검색:",
            paginate: {
                first: "처음",
                last: "마지막",
                next: "다음",
                previous: "이전"
            },
        },
        drawCallback: function (settings) {
            var api = this.api();
            var data = api.data().count(); // 총 목록 수
            if (data === 0) {
                $('.dataTables_paginate').hide(); // 페이징 숨기기
            } else {
                $('.dataTables_paginate').show(); // 페이징 보이기
            }
        },
    });
}

function init(){
    plan.util.getAuthenticatedUserInfo().done(function(userInfo) {
        let isAdmin = userInfo.roles.some(function(role) {
            return role.roleName === "ROLE_ADMIN";
        });
        if (isAdmin) {
            $('#createBtn').show();
            $('#noticeControl').show();
        } else {
            $('#createBtn').hide();
            $('#noticeControl').hide();
        }
        dataTableInit(isAdmin);
    }).fail(function(error) {
        console.error(error);
        $('#createBtn').hide();
    });

}

function eventListener() {
    $('#saveNoticeBtn').on('click', function () {
        const noticeId = $(this).data('id');
        const notice = plan.util.fetchFormData('noticeForm');
        const validation = isValidation(notice);
        if(validation) {
            if (noticeId) {
                updateNotice(noticeId, notice);
            } else {
                createNotice(notice);
            }
        }
    });

    // 등록버튼 클릭 시 발생
    $('#createBtn').on('click', function () {
        plan.util.resetForm('noticeForm');
        $('#saveNoticeBtn').removeData('id');
    });
}

function createNotice(notice) {
    plan.util.AJAX_Json('/admin/api/notices', notice, 'POST', 'json').done((response) => {
        Toast.fire({
            icon: 'success',
            title: response.message,
        }).then(() => {
            $('#createModal').modal('hide');
            reloadList();
        });
    }).fail(showError);
}

function updateNotice(noticeId, notice) {
    plan.util.AJAX_Json(`/admin/api/notices/${noticeId}`, notice, 'PUT', 'json').done((response) => {
        Toast.fire({
            icon: 'success',
            title: response.message,
        }).then(() => {
            $('#createModal').modal('hide');
            reloadList();
        });
    }).fail(showError);
}

function deleteNotice(noticeId) {
    Swal.fire({
        title: "삭제",
        text: "공지사항을 삭제 하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            plan.util.AJAX_Json(`/admin/api/notices/${noticeId}`, '', 'DELETE', 'json').done((response) => {
                Toast.fire({
                    icon: 'success',
                    title: response.message,
                }).then(() => {
                    $('#createModal').modal('hide');
                    reloadList();
                });
            }).fail(showError);
        }
    });
}

function viewDetail(noticeId) {
    plan.util.AJAX_Json(`/api/notices/${noticeId}`, '', 'GET', 'json').done((response) => {
        $('#regDt').text(formatDate(response.data.regDt));
        $('#regName').text(response.data.regName);
        $('#detailTitle').text(response.data.title);
        $('#detailContent').text(response.data.content);
        $('#detailModal').modal('show');
    }).fail(showError);
}

function editNotice(noticeId) {
    plan.util.AJAX_Json(`/admin/api/notices/${noticeId}`, '', 'GET', 'json').done((response) => {
        $('#title').val(response.data.title);
        $('#content').val(response.data.content);
        $('#saveNoticeBtn').data('id', noticeId);
        $('#createModal').modal('show');
    });
}

function reloadList() {
    $('#noticeTable').DataTable().ajax.reload();
}

// 에러처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.message : '알 수 없는 오류';
    Swal.fire({ title: "오류", text: errorMessage, icon: "error" });
}
// 날짜 형식
function formatDate(data) {
    return data ? data.slice(0, 16).replace('T', ' ') : '-';
}

function isValidation(data){

    if(plan.util.isEmpty(data.title)){
        Toast.fire({icon : 'warning', title : '제목을 확인해주세요.'});
        return false;
    } else if(plan.util.isEmpty(data.content)){
        Toast.fire({icon : 'warning', title : '내용를 확인해주세요.'});
        return false;
    }
    return true;
}