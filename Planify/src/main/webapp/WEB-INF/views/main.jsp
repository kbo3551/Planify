<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/resources/js/bundle/fullcalendar-6.1.15/custom-css/fullcalendar-custom.css" rel="stylesheet" type="text/css">

<div class="col-lg-12">
    <div class="card mb-4">
		<div id='calendar' style="width: 100%;"></div>
	</div>
</div>
<!-- Modal for adding/updating events -->
<div class="modal fade" id="todoModal" tabindex="-1" aria-labelledby="todoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="todoModalLabel">일정 추가/수정</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">X</button>
            </div>
            <div class="modal-body">
                <form id="todoForm">
                    <input type="hidden" id="todoId" name="todoId">
                    <div class="mb-3">
                        <label for="title" class="form-label">일정 제목</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="todoStart" class="form-label">시작 날짜</label>
                        <input type="date" class="form-control" id="todoStart" name="todoStart" required>
                    </div>
                    <div class="mb-3">
                        <label for="todoEnd" class="form-label">종료 날짜</label>
                        <input type="date" class="form-control" id="todoEnd" name="todoEnd">
                    </div>
                    <div class="mb-3">
                        <label for="todoEnd" class="form-label">일정 내용</label>
                        <input type="text" class="form-control" id="description" name="description">
                    </div>
                    
                    <button type="button" id="todoSaveBtn" class="btn btn-primary">저장</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="/resources/js/bundle/fullcalendar-6.1.15/index.global.min.js"></script>
<script type="text/javascript">
/** 
 * 테스트 fullcalendar
 * TODO : 작업 시 동적 데이터 통신 및 다듬기 
 */
document.addEventListener('DOMContentLoaded', function () {
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
            plan.util.AJAX_Json(`/api/todos/member/${memberNo}`, '', 'GET', 'json').done(function (response) {
                if (response.status === 200) {
                    const events = response.data.map(todo => ({
                        id: todo.id,
                        title: todo.title,
                        start: todo.startDate,
                        end: todo.endDate
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
            $('#todoStart').val(event.startStr);
            $('#todoEnd').val(event.endStr);
            $('#description').val(event.description);
            $('#todoModal').modal('show');
        },
        dateClick: function (info) {
            $('#todoId').val('');
            $('#title').val('');
            $('#todoStart').val(info.dateStr);
            $('#todoEnd').val('');
            $('#description').val('');
            $('#todoModal').modal('show');
        }
	});
	
	$('#todoSaveBtn').on('click', function (e) {
	    e.preventDefault();
	    const id = $('#todoId').val();
	    const todo = plan.util.fetchFormData('todoForm');
	    
	    todo.memberNo = '${memberNo}';
	    
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
	
	calendar.render();
});
</script>

</body>
</html>