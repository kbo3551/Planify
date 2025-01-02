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
                Swal.fire({
                  title: "회원가입 성공",
                  text: response.message,
                  icon: "success",
                  timer: 1000
                }).then(() => {
                    window.location.href = '/login';
                });
            }
        }).fail(function(jqXHR) {
            const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
            Swal.fire({
                title: "오류",
                text: errorMessage,
                icon: "error"
            });
        });
    });
});