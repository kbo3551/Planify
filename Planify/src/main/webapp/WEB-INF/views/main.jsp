<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-lg-12">

<!-- Default Card Example -->
    <div class="card mb-4">
		Main.jsp
		<br>
		H2접속 경로 : <a href="http://localhost:8080/h2-console">http://localhost:8080/h2-console</a>
		<br>
		${memberId}
		<br>
		<a href="/logout">로그아웃</a>
		<br>
		<div id='calendar' style="width: 50%;"></div>
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
		events: [
		    { title: '회의', start: '2024-12-20' },
		    { title: '김장', start: '2024-12-22', end: '2024-12-23' },
		    { title: '크리스마스 파티', start: '2024-12-23', end: '2024-12-26' }
		]
	});
	
	calendar.render();
});
</script>

</body>
</html>