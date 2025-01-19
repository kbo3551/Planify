<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container-fluid">

    <!-- Error Text -->
    <div class="text-center">
        <div class="error mx-auto" data-text="${errorCode}">${errorCode}</div>
        <p class="lead text-gray-800 mb-5">${errorMessage}</p>
        <p class="text-gray-500 mb-0"></p>
        <a href="/main">&larr; 메인으로 돌아가기</a>
    </div>

</div>