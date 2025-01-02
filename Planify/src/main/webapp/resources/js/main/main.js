/**
 * 테스트 fullcalendar
 * TODO : 작업 시 동적 데이터 통신 및 다듬기
 * 
 */
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
        // TODO : 이벤트 다듬기 (유효성,잘못된 수정, 잘못된 이벤트 등)
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
                    alert('일정 데이터를 불러오는데 실패했습니다.');
                }
            }).fail(function () {
                alert('일정 데이터를 불러오는데 실패했습니다.');
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
        const id = $('#todoId').val();
        const todo = plan.util.fetchFormData('todoForm');

        todo.memberNo = $('#userInfo').data('number');

        todo.startDate = new Date($('#todoStart').val()).toISOString();
        todo.endDate = new Date($('#todoEnd').val()).toISOString();

        if (id) {
            // 수정
            plan.util.AJAX_Json(`/api/todos/${id}`, todo, 'PUT', 'json').done(function (response) {
                if (response.status === 200) {
                    calendar.refetchEvents();
                    $('#todoModal').modal('hide');
                } else {
                    alert('일정 수정에 실패했습니다.');
                }
            }).fail(function () {
                alert('error');
            });
        } else {
            // 생성
            plan.util.AJAX_Json('/api/todo', todo, 'POST', 'json').done(function (response) {
                if (response.status === 200) {
                    calendar.refetchEvents();
                    $('#todoModal').modal('hide');
                } else {
                    alert('일정 추가에 실패했습니다.');
                }
            }).fail(function () {
                alert('error');
            });
        }
    });

    $('#modal-close').on('click', function (){
        plan.util.resetForm('todoForm');
        $('#todoModal').modal('hide');
    });

    $('#todoRemoveBtn').on('click', function(){
        var flag = confirm('일정을 삭제 하시겠습니까?');
        if(flag){
            const id = $('#todoId').val();
            plan.util.AJAX_Json(`/api/todos/${id}`, '', 'DELETE', 'json').done(function (response) {
                if (response.status === 200) {
                    calendar.refetchEvents();
                    $('#todoModal').modal('hide');
                } else {
                    alert('일정 삭제에 실패했습니다.');
                }
            }).fail(function () {
                alert('error');
            }); 
        }
    });
    
    calendar.render();
});