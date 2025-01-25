<%--
  Created by IntelliJ IDEA.
  User: boryeong
  Date: 2025-01-25
  Time: 오후 4:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card shadow mb-4">
    <div class="card-header py-3 d-flex justify-content-between align-items-center">
        <h6 class="m-0 font-weight-bold text-primary">실시간 채팅</h6>
        <button type="button" class="btn btn-primary" id="createBtn" style="display: none;" data-toggle="modal" data-target="#createModal">등록</button>
    </div>
    <div class="card-body" >
        <div id="chat-box" class="border rounded p-3 mb-3" style="height: 300px; overflow-y: scroll;"></div>
        <div class="input-group" id="chatArea">
            <input id="messageInput" type="text" class="form-control mr-2" placeholder="메시지를 입력하세요.">
            <button id="sendButton" class="btn btn-primary">전송</button>
        </div>
    </div>
</div>

<script src="/resources/js/bundle/sockjs/sockjs.min.js"></script>
<script src="/resources/js/bundle/stomp/stomp.umd.min.js"></script>
<script src="/resources/js/chat/chat.js"></script>