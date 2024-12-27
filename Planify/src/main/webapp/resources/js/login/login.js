/**
 * login.js 
 */

$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        const memberId = $('#memberId').val();
        const password = $('#password').val();

        plan.util.AJAX_Json('/api/login', { memberId: memberId, password: password }, 'POST', 'json').done(function(response) {
            alert(response.message);
            if( response.status === 200 ){
                window.location.href = response.data.redirectUrl;
            }
        }).fail(function(jqXHR) {
            const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
            alert('로그인 실패: ' + errorMessage);
        });
    });
});