<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="card shadow mb-4">
    <div class="card-header py-3 d-flex justify-content-between align-items-center">
        <h6 class="m-0 font-weight-bold text-primary">마이 페이지</h6>
    </div>

    <div class="card-body">
        <form id="profileForm">
            <div class="form-group">
                <label for="nickName">닉네임</label>
                <input type="text" class="form-control" id="nickName" name="nickName">
            </div>
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" class="form-control" id="name" name="name">
            </div>
            <div class="form-group">
                <label for="gender">성별</label>
                <select class="form-control" id="gender" name="gender">
                    <option value="MALE">남자</option>
                    <option value="FEMALE">여자</option>
                    <option value="UNKNOWN">알수없음</option>
                </select>
            </div>
            <div class="form-group">
                <label for="password">새로운 비밀번호</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
            </div>  
            <button type="button" class="btn btn-primary" id="saveProfile">저장</button>
        </form>
    </div>
</div>

<script src="/resources/js/profile/profile.js"></script>
