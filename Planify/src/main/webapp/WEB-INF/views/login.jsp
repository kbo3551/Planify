<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <h2>로그인</h2>
    <form id="loginForm">
        <label for="memberId">아이디:</label>
        <input type="text" id="memberId" required /><br/><br/>
    
        <label for="password">비밀번호:</label>
        <input type="password" id="password" required /><br/><br/>
    
        <button type="submit">로그인</button>
    </form>
    <br/>
    <a href="/register">회원가입 페이지로 이동</a>
<script>
$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        const memberId = $('#memberId').val();
        const password = $('#password').val();

        $.ajax({
            url: '/api/login',
            type: 'POST',
            contentType: 'application/json; charset=UTF-8',
            xhrFields: {
                withCredentials: true // 세션 유지
            },
            data: JSON.stringify({ memberId: memberId, password: password }),
            success: function(response) {
                alert(response.message);
                window.location.href = response.redirectUrl;
            },
            error: function(xhr) {
                const errorMessage = xhr.responseJSON ? xhr.responseJSON.error : '알 수 없는 오류';
                alert('로그인 실패: ' + errorMessage);
            }
        });
    });
});
</script>
