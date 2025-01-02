/**
 * login.js 
 */

$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        const memberId = $('#memberId').val();
        const password = $('#password').val();

        plan.util.AJAX_Json('/api/login', { memberId: memberId, password: password }, 'POST', 'json').done(function(response) {
            
            if( response.status === 200 ){
                Swal.fire({
                  title: "로그인 성공",
                  text: response.message,
                  icon: "success",
                  timer: 1000
                }).then(() => {
                    window.location.href = response.data.redirectUrl;
                });
            } else if( response.status === 401){
                Swal.fire({
                  title: "확인해주세요.",
                  text: response.message,
                  icon: "warning"
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