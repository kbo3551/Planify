<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <h2>회원가입</h2>
    <form id="registerForm">
	    <label for="memberId">아이디:</label>
	    <input type="text" name="memberId" id="memberId" required /><br/><br/>
	
	    <label for="password">비밀번호:</label>
	    <input type="password" name="password" id="password" required /><br/><br/>
	
	    <label for="nickName">닉네임:</label>
	    <input type="text" name="nickName" id="nickName" required /><br/><br/>
	
	    <label for="name">이름:</label>
	    <input type="text" name="name" id="name" required /><br/><br/>
	
	    <label for="gender">성별:</label>
	    <select name="gender" id="gender" required>
	        <option value="M">남자</option>
	        <option value="W">여자</option>
	    </select><br/><br/>
	
	    <button type="submit">회원가입</button>
	</form>
    <br/>
    <a href="/login">로그인 페이지로 이동</a>
<script>
$(document).ready(function() {
	$('#registerForm').submit(function(event) {
	    event.preventDefault();

	    const data = {
	        memberId: $('#memberId').val(),
	        password: $('#password').val(),
	        nickName: $('#nickName').val(),
	        name: $('#name').val(),
	        gender: $('#gender').val()
	    };

	    $.ajax({
	        url: '/api/login/register',
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(data), // JSON 형식으로 보냄
	        success: function(response) {
	            alert('회원가입 성공');
	            window.location.href = '/login';
	        },
	        error: function(xhr) {
	            alert('회원가입 실패: ' + xhr.responseJSON.error);
	        }
	    });
	});
});
</script>
