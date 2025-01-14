<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow mb-4">
	<div class="card-header py-3">
	    <h6 class="m-0 font-weight-bold text-primary">NoticeList</h6>
	</div>
	<div class="card-body">
	    <div class="table-responsive">
	        <table class="table table-bordered" id="noticeTable" width="100%" cellspacing="0">
	            <thead>
	                <tr>
	                    <th>번호</th>
	                    <th>제목</th>
	                    <th>내용</th>
	                    <th>작성자</th>
	                    <th>등록일</th>
	                </tr>
	            </thead>
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	</div>
</div>

<script src="/resources/js/notice/noticeList.js"></script>