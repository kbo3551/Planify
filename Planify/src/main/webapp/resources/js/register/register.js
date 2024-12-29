/**
 * register.js 
 */

$(document).ready(function() {
    $('#registerForm').submit(function(event) {
        event.preventDefault();

        const data = plan.util.fetchFormData('registerForm');

        plan.util.AJAX_Json('/api/login/register', data, 'POST', 'json').done(function(response) {
            alert(response.message);
            if( response.status === 200 ){
                window.location.href = '/login';
            }
        }).fail(function(jqXHR) {
            const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
            alert('회원가입 실패: ' + errorMessage);
        });
    });
});