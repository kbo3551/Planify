let isAdmin = false;
$(document).ready(function () {
    dataTableInit();
    init();
    eventListener();
});

function dataTableInit() {
    const columns = [
        { data: 'index' },
        { data: 'title', render: (data, type, row) => `<a href="#" onclick="viewDetail(${row.noticeId})">${data}</a>` },
        { data: 'regName' },
        { data: 'regDt', render: formatDate }
    ];

    // 관리자인 경우에만 수정/삭제 버튼 컬럼 추가
    if (isAdmin) {
        columns.push({
            data: null,
            render: (data, type, row) => `
                <button class="btn btn-sm btn-warning" onclick="editNotice(${row.noticeId})">수정</button>
                <button class="btn btn-sm btn-danger" onclick="deleteNotice(${row.noticeId})">삭제</button>
            `
        });
    }

    // DataTable 초기화
    $('#noticeTable').DataTable({
        ajax: function (data, callback, settings) {
            plan.util.AJAX_Json('/api/notices', '', 'GET', 'json')
                .done(function (response) {
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
                })
                .fail(showError);
        },
        columns: columns, // 동적으로 생성된 컬럼 설정
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
        isAdmin = userInfo.roles.some(function(role) {
            return role.roleName === "ROLE_ADMIN";
        });
        if (isAdmin) {
            $('#createBtn').show();
            $('#noticeControl').show();
        } else {
            $('#createBtn').hide();
            $('#noticeControl').hide();
        }
    }).fail(function(error) {
        console.error(error);
        $('#createBtn').hide();
    });
}

function eventListener() {
    $('#saveNoticeBtn').on('click', function () {
        const noticeId = $(this).data('id');
        if (noticeId) {
            updateNotice(noticeId);
        } else {
            createNotice();
        }
    });
}

function createNotice() {
    const notice = getNoticeFormData();
    plan.util.AJAX_Json('/api/notices', notice, 'POST', 'json').done(() => {
        alert('공지사항이 등록되었습니다.'); 
        reloadList(); 
    });
}

function updateNotice(noticeId) {
    const notice = getNoticeFormData();
    plan.util.AJAX_Json(`/api/notices/${noticeId}`, notice, 'PUT', 'json').done(() => { 
        alert('공지사항이 수정되었습니다.'); 
        reloadList(); 
    });
}

function deleteNotice(noticeId) {
    if (confirm('정말 삭제하시겠습니까?')) {
        plan.util.AJAX_Json(`/api/notices/${noticeId}`, '', 'DELETE', 'json').done(() => { 
            alert('공지사항이 삭제되었습니다.'); 
            reloadList(); 
        });
    }
}

function viewDetail(noticeId) {
    plan.util.AJAX_Json(`/api/notices/${noticeId}`, '', 'GET', 'json')
        .done((response) => {
            $('#detailTitle').text(response.data.title);
            $('#detailContent').text(response.data.content);
            $('#detailModal').modal('show');
        });
}

function editNotice(noticeId) {
    plan.util.AJAX_Json(`/api/notices/${noticeId}`, '', 'GET', 'json')
        .done((response) => {
            $('#titleInput').val(response.data.title);
            $('#contentInput').val(response.data.content);
            $('#saveNoticeBtn').data('id', noticeId);
            $('#createModal').modal('show');
        });
}

function getNoticeFormData() {
    return {
        title: $('#titleInput').val(),
        content: $('#contentInput').val(),
    };
}

function reloadList() {
    $('#noticeTable').DataTable().ajax.reload();
}

// 에러처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
    Swal.fire({ title: "오류", text: errorMessage, icon: "error" });
}
// 날짜 형식
function formatDate(data) {
    return data ? data.slice(0, 16).replace('T', ' ') : '-';
}