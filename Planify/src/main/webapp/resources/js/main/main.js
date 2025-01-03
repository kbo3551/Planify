/**
 * 테스트 fullcalendar
 * TODO : 작업 시 동적 데이터 통신 및 다듬기
 * 
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
            const memberNo= $('#userInfo').data('number');
            plan.util.AJAX_Json(`/api/todos/member/`+ memberNo, '', 'GET', 'json').done(function (response) {
                if (response.status === 200) {
                    const events = response.data.map(todo => ({
                        id: todo.todoId,
                        title: todo.title,
                        start: todo.startDate,
                        end: todo.endDate,
                        etc : todo.description
                    }));
                    successCallback(events);
                } else {
                    Swal.fire({
                      title: "오류",
                      text: '일정 데이터를 불러오는데 실패했습니다.',
                      icon: "error"
                    });
                }
            }).fail(function () {
                Swal.fire({
                  title: "오류",
                  text: '일정 데이터를 불러오는데 실패했습니다.',
                  icon: "error"
                });
            });
        },
        eventClick: function (info) {
            const event = info.event;
            $('#todoId').val(event.id);
            $('#title').val(event.title);
            $('#todoStart').val(event.startStr.slice(0, 16));
            $('#todoEnd').val(event.endStr.slice(0, 16));
            $('#description').val(event.extendedProps.etc);
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

    $('#todoSaveBtn').on('click', function (e) {
        e.preventDefault();

        var params = plan.util.fetchFormData('todoForm');
        const validation = isValidation(params);
        // const startDay = new Date($('#todoStart').val());
        // const endDay = new Date($('#todoEnd').val());

        if(validation) {

            const id = $('#todoId').val();
            const todo = plan.util.fetchFormData('todoForm');

            todo.memberNo = $('#userInfo').data('number');

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
                }).fail(function(jqXHR) {
                    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
                    Swal.fire({
                        title: "오류",
                        text: errorMessage,
                        icon: "error"
                    });
                });
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
                            text: '일정 추가에 실패했습니다.',
                            icon: "error"
                        });
                    }
                }).fail(function(jqXHR) {
                    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
                    Swal.fire({
                        title: "오류",
                        text: errorMessage,
                        icon: "error"
                    });
                });

            }
        }
    });

    $('#modal-close').on('click', function (){
        plan.util.resetForm('todoForm');
        $('#todoModal').modal('hide');
    });

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
                }).fail(function(jqXHR) {
                    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
                    Swal.fire({
                        title: "오류",
                        text: errorMessage,
                        icon: "error"
                    });
                });
            }
        });
    });
    
    calendar.render();
});

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