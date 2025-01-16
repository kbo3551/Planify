<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow mb-4">
	<div class="card-header py-3 d-flex justify-content-between align-items-center">
	    <h6 class="m-0 font-weight-bold text-primary">NoticeList</h6>
	    <button type="button" class="btn btn-primary" id="createBtn" style="display: none;" data-toggle="modal" data-target="#createModal">등록</button>
	</div>

	<div class="card-body">
	    <div class="table-responsive">
	        <table class="table table-bordered" id="noticeTable" width="100%" cellspacing="0">
	            <thead>
	                <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>등록일</th>
                        <th id="noticeControl" style="display: none;">관리</th>
	                </tr>
	            </thead>
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	</div>
</div>

<!-- 등록/수정 모달 -->
<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createModalLabel">공지사항 등록</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="noticeForm">
                    <div class="form-group">
                        <label for="titleInput">제목</label>
                        <input type="text" class="form-control" id="titleInput" placeholder="제목을 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="contentInput">내용</label>
                        <textarea class="form-control" id="contentInput" rows="5" placeholder="내용을 입력하세요"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="saveNoticeBtn">저장</button>
            </div>
        </div>
    </div>
</div>

<!-- 상세보기 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="detailModalLabel">공지사항 상세</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p><strong>제목:</strong> <span id="detailTitle"></span></p>
                <p><strong>내용:</strong></p>
                <p id="detailContent"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<script src="/resources/js/notice/notice.js"></script>