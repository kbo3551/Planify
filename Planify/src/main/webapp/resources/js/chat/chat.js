let sender = ''; // 표시될 닉네임

$(document).ready(function () {

    // 엔터 키 입력
    $('#messageInput').on('keydown', function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            sendMessage();
        }
    });

    // 메시지 전송 버튼 클릭 시 발생
    $('#sendButton').on('click',function () {
        sendMessage();
    });
    // 연결
    webSocketConnect();
    // 기존 채팅 불러오기
    loadMessages();
    // 닉네임
    getSender();
    init();
});

function sendMessage() {
    const messageContent = $('#messageInput').val();
    const isNotice = $('#isNotice').is(':checked');

    if (!plan.util.isEmpty(messageContent)) {

        plan.util.AJAX_Json(`/api/chat/messages`,{ sender: sender, content: messageContent, isNotice: isNotice }, 'POST', 'json').done((response) => {
            $('#messageInput').val('');
        }).fail(showError);
    }
}

// 접속 사용자 닉네임 가져오기
function getSender(){
    plan.util.getAuthenticatedUserInfo().done((userInfo)  => {
        sender = userInfo.nickName; // 닉네임 저장
    }).fail(function (error) {
        console.error('Failed to load user info:', error);
        sender = 'UNKNOWN'; // 기본값 설정
    });
}

// 채팅 불러오기
function renderMessage(message) {
    const chatBox = $('#chat-box');

    const messageHtml = message.isNotice ? `<div style="color: red;"><span class="text-muted">(${message.sentDt})</span> <strong>[공지사항]</strong> ${message.content}</div>`
                                                : `<div><span class="text-muted">(${message.sentDt})</span> <strong>${message.sender}:</strong> ${message.content}</div>`;

    chatBox.append(messageHtml);
    chatBox.scrollTop(chatBox[0].scrollHeight);
}

function loadMessages() {
    $.get("/api/chat/messages", function (messages) {
        messages.forEach(renderMessage);
    });
}

// WebSocket 연결
function webSocketConnect (){
    // WebSocket 설정
    const socket = new SockJS('/ws');
    const stompClient = StompJs.Stomp.over(socket);
    // WebSocket 연결
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/messages', function (response) {
            const message = JSON.parse(response.body);
            renderMessage(message);
        });
    });
}

function init(){

    plan.util.getAuthenticatedUserInfo().done(function(userInfo) {
        let isAdmin = userInfo.roles.some(function(role) {
            return role.roleName === "ROLE_ADMIN";
        });
        if (isAdmin) {
            const notice = `<div class="form-check mt-2 mr-2" >
                                       <input type="checkbox" class="form-check-input" id="isNotice">
                                       <label class="form-check-label" for="isNotice">공지 여부</label>
                                   </div>`;
            $('#chatArea').prepend(notice);
        }
    }).fail(function(error) {
        console.error(error);
    });
}

// 에러처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.message : '알 수 없는 오류';
    Swal.fire({ title: "오류", text: errorMessage, icon: "error" });
}