/**
 * 일정관리 [fullcalendar]
 */
const Toast = plan.util.getToast();
$(document).ready(function() {

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'ko', // 언어팩 한국어 지정
        initialView: 'dayGridMonth',
        contentHeight: 'auto', // 캘린더 높이 조절
        headerToolbar: {
            left: 'prev,next today', // 이전, 다음, 오늘 버튼
            center: 'title',  // 달력 제목
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth' // 월, 주, 일, 일정목록 지정
        },
        views: {
            dayGridMonth: { buttonText: '월' },
            timeGridWeek: { buttonText: '주' },
            timeGridDay: { buttonText: '일' },
            listMonth: { buttonText: '일정목록' }
        },
        events: function (info, successCallback, failureCallback) {
            // 일정 불러오기
            plan.util.AJAX_Json(`/api/todos/member`, '', 'GET', 'json').done(function (response) {
                if (response.status === 200) {
                    const events = response.data.map(todo => {
                        let backgroundColor, borderColor, textColor;
                        // 상태에 따른 색깔 변경
                        switch (todo.status) {
                            case "Pending":
                                // 주황
                                backgroundColor = "#FF8A65";
                                borderColor = "#FF7043";
                                textColor = "#FFFFFF";
                                break;
                            case "Progress":
                                // 파랑
                                backgroundColor = "#81D4FA";
                                borderColor = "#4FC3F7";
                                textColor = "#FFFFFF";
                                break;
                            case "Completed":
                                // 초록
                                backgroundColor = "#A5D6A7";
                                borderColor = "#81C784";
                                textColor = "#FFFFFF";
                                break;
                            default:
                                backgroundColor = "#FF8A65";
                                borderColor = "#FF7043";
                                textColor = "#FFFFFF";
                                break;
                        }
                
                        return {
                            id: todo.todoId,
                            title: todo.title,
                            start: todo.startDate,
                            end: todo.endDate,
                            etc: todo.description,
                            status: todo.status,
                            backgroundColor: backgroundColor,
                            borderColor: borderColor,
                            textColor: textColor
                        };
                    });
                
                    successCallback(events);
                } else {
                    Swal.fire({
                      title: "오류",
                      text: response.message,
                      icon: "error"
                    });
                }
            }).fail(showError);
        },
        eventClick: function (info) {
            const event = info.event;
            $('#todoId').val(event.id);
            $('#title').val(event.title);
            $('#todoStart').val(event.startStr.slice(0, 16));
            $('#todoEnd').val(event.endStr.slice(0, 16));
            $('#description').val(event.extendedProps.etc);
            $('#status').val(event.extendedProps.status);
            $('#todoModal').modal('show');
            $('#todoRemoveBtn').show();
        },
        dateClick: function (info) {
            // 선택한 날짜
            let selectedDate = new Date(info.date);
            // 선택한 날짜 디폴트 12:00 AM 
            let startDate = new Date(selectedDate.setHours(0, 0, 0, 0));
            // +1일 12:00 AM
            let nextDate = new Date(info.date);
            let endDate = new Date(nextDate.setDate(nextDate.getDate() + 1));
            endDate.setHours(0, 0, 0, 0);
            // ISO 변환
            const startDates = startDate.toISOString().slice(0, 16);
            const endDates = endDate.toISOString().slice(0, 16);

            $('#todoId').val('');
            $('#title').val('');
            // $('#todoStart').val(plan.util.getToDateTimeISO(0));
            // $('#todoEnd').val(plan.util.getToDateTimeISO(1));
            $('#todoStart').val(startDates);
            $('#todoEnd').val(endDates);
            $('#description').val('');
            $('#todoModal').modal('show');
            $('#todoRemoveBtn').hide();
        }
    });

    // 저장 버튼 클릭 시 발생
    $('#todoSaveBtn').on('click', function (e) {
        e.preventDefault();

        var params = plan.util.fetchFormData('todoForm');
        const validation = isValidation(params);
        // const startDay = new Date($('#todoStart').val());
        // const endDay = new Date($('#todoEnd').val());

        if(validation) {

            const id = $('#todoId').val();
            const todo = plan.util.fetchFormData('todoForm');

            // todo.startDate = new Date($('#todoStart').val()).toISOString();
            // todo.endDate = new Date($('#todoEnd').val()).toISOString();
            // 저장할땐 기본 input에서 받아온 값으로 해야 오차범위 x
            todo.startDate = $('#todoStart').val();
            todo.endDate = $('#todoEnd').val();

            if (id) {
                // 수정
                plan.util.AJAX_Json(`/api/todos/${id}`, todo, 'PUT', 'json').done(function (response) {
                    if (response.status === 200) {
                        calendar.refetchEvents();
                        Toast.fire({
                            icon: 'success',
                            title: response.message,
                        }).then(() => {
                            $('#todoModal').modal('hide');
                        });
                    } else {
                        Swal.fire({
                            title: "오류",
                            text: '일정 수정에 실패했습니다.',
                            icon: "error"
                        });
                    }
                }).fail(showError);
            } else {
                // 생성
                plan.util.AJAX_Json('/api/todo', todo, 'POST', 'json').done(function (response) {
                    if (response.status === 200) {
                        calendar.refetchEvents();
                        Toast.fire({
                            icon: 'success',
                            title: response.message,
                        }).then(() => {
                            $('#todoModal').modal('hide');
                        });
                    } else {
                        Swal.fire({
                            title: "오류",
                            text: response.message,
                            icon: "error"
                        });
                    }
                }).fail(showError);
            }
        }
    });
    
    // 팝업 닫기
    $('#modal-close').on('click', function (){
        plan.util.resetForm('todoForm');
        $('#todoModal').modal('hide');
    });

    // 일정 삭제 버튼 클릭 시 발생
    $('#todoRemoveBtn').on('click', function(){
        
        Swal.fire({
            title: "삭제",
            text: "일정을 삭제 하시겠습니까?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "확인",
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                const id = $('#todoId').val();
                plan.util.AJAX_Json(`/api/todos/${id}`, '', 'DELETE', 'json').done(function (response) {
                    if (response.status === 200) {
                        calendar.refetchEvents();
                        Toast.fire({
                            icon: 'success',
                            title: response.message,
                        }).then(() => {
                            $('#todoModal').modal('hide');
                        });
                    } else {
                        Swal.fire({
                            title: "오류",
                            text: '일정 삭제에 실패했습니다.',
                            icon: "error"
                        });
                    }
                }).fail(showError);
            }
        });
    });
    
    calendar.render();
});

// 유효성 체크
function isValidation(data){
    const startDay = new Date($('#todoStart').val());
    const endDay = new Date($('#todoEnd').val());
    if(plan.util.isEmpty(data.title)){
        Toast.fire({
            icon : 'warning',
            title : '제목을 확인해주세요.'
        });
        return false;
    } else if(plan.util.isEmpty(data.todoEnd) || plan.util.isEmpty(data.todoStart)){
        Toast.fire({
            icon : 'warning',
            title : '날짜를 확인해주세요.'
        });
        return false;
    } else if( startDay >= endDay ) {
        Toast.fire({
            icon: 'warning',
            title: '종료날짜가 시작날짜보다<br> 같거나 작을 수 없습니다.'
        });
        return false;
    }

    return true;
}

// 에러처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.message : '알 수 없는 오류';
    Swal.fire({ title: "오류", text: errorMessage, icon: "error" });
}