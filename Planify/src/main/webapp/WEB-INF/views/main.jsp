<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/resources/js/bundle/fullcalendar-6.1.15/custom-css/fullcalendar-custom.css" rel="stylesheet" type="text/css">

<div class="col-lg-12">
    <div class="card mb-4">
		<div id='calendar' style="width: 100%;"></div>
	</div>
</div>
<!-- 입력 수정 모달 -->
<div class="modal fade" id="todoModal" tabindex="-1" aria-labelledby="todoModalLabel" role="dialog" aria-hidden="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="todoModalLabel">일정 추가/수정</h5>
                <button type="button" class="close" id="modal-close" data-bs-dismiss="modal" aria-label="Close">X</button>
            </div>
            <div class="modal-body">
                <form id="todoForm">
                    <input type="hidden" id="todoId" name="todoId">
                    <div class="mb-3">
                        <label for="title" class="form-label">일정 제목</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">일정 상태</label>
                        <select type="text" class="form-control" id="status" name="status" required>
                            <option value="Pending">대기</option>
                            <option value="Progress">진행</option>
                            <option value="Completed">완료</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="todoStart" class="form-label">시작 날짜</label>
                        <input type="datetime-local" class="form-control" id="todoStart" name="todoStart" required>
                    </div>
                    <div class="mb-3">
                        <label for="todoEnd" class="form-label">종료 날짜</label>
                        <input type="datetime-local" class="form-control" id="todoEnd" name="todoEnd">
                    </div>
                    <div class="mb-3">
                        <label for="todoEnd" class="form-label">일정 내용</label>
<!--                         <input type="text" class="form-control" id="description" name="description"> -->
                        <textarea class="form-control" id="description" name="description" rows="3" cols="3" style="resize: none;"></textarea>
                    </div>
                    
                    <button type="button" id="todoSaveBtn" class="btn btn-primary">저장</button>
                    <button type="button" id="todoRemoveBtn" class="btn btn-danger" style="display: none;">삭제</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%-- <div style="display: block" id="userInfo" data-number="<c:out escapeXml="true" value="${memberNo}" />"></div> --%>

<script src="/resources/js/bundle/fullcalendar-6.1.15/index.global.min.js"></script>
<script src="/resources/js/main/main.js"></script>
<script type="text/javascript"></script>
