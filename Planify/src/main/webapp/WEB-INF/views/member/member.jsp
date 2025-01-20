<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow mb-4">
    <div class="card-header py-3 d-flex justify-content-between align-items-center">
        <h6 class="m-0 font-weight-bold text-primary">사용자 관리</h6>
        <button type="button" class="btn btn-primary" id="createUserBtn" data-toggle="modal" data-target="#createUserModal">등록</button>
    </div>

    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="userTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>아이디</th>
                        <th>닉네임</th>
                        <th>이름</th>
                        <th>성별</th>
                        <th>권한</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- 사용자 등록/수정 모달 -->
<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog" aria-labelledby="createUserModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createUserModalLabel">사용자 등록/수정</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="userForm">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="memberId">아이디</label>
                        <input type="text" class="form-control" id="memberId" name="memberId" placeholder="아이디를 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="nickName">닉네임</label>
                        <input type="text" class="form-control" id="nickName" name="nickName" placeholder="닉네임을 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="gender">성별</label>
                        <select class="form-control" id="gender" name="gender">
                            <option value="MALE">남자</option>
                            <option value="FEMALE">여자</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>권한</label>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="roleUser" name="roles" value="ROLE_USER">
                            <label class="form-check-label" for="roleUser">ROLE_USER</label>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="roleAdmin" name="roles" value="ROLE_ADMIN">
                            <label class="form-check-label" for="roleAdmin">ROLE_ADMIN</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" id="saveUserBtn">저장</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/resources/js/member/member.js"></script>