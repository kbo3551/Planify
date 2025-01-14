$(document).ready(function () {
    dataTableInit();
});

function dataTableInit(){
    // dataTable init
    $('#noticeTable').DataTable({
        ajax: function (data, callback, settings) {
            plan.util.AJAX_Json('/api/notices', '', 'GET', 'json')
                .done(function (response) {
                    if (response.status === 200) {
                        // 순번
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
                .fail(function (jqXHR) {
                    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
                    Swal.fire({
                        title: "오류",
                        text: errorMessage,
                        icon: "error"
                    });
                    callback({ data: [] });
                });
        },
        // 테이블 레코드
        columns: [
            { data: 'index' },
            { data: 'title' },
            { data: 'content' },
            { data: 'regName' },
            { data: 'regDt'
                , render: function (data) { 
                    // YYYY-MM-DD HH:MM으로 출력 
                    if (!data) return '-';
                    return data.slice(0, 16).replace('T', ' ');
                }
            },
        ],
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