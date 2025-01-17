/**
 * profile.js
 */
const Toast = plan.util.getToast();
$(document).ready(function () {
    
    // 저장버튼 클릭 시 발생
    $('#saveProfile').on('click',function () {

        const formData = plan.util.fetchFormData('profileForm');
        
        plan.util.AJAX_Json('/api/member/profile/update', formData, 'PUT', 'json').done(function (response) {
            if (response.status === 200) {
                Toast.fire({icon: 'success',title: response.message,});
                loadInfo();
            } else {
                Swal.fire({
                    title: 'Error',
                    text: response.message,
                    icon: 'error'
                });
            }
        }).fail(showError);
    });
    
    // 데이터 로드
    loadInfo();
});

function loadInfo(){
    
    plan.util.AJAX_Json('/api/member/profile', '', 'GET', 'json').done(function (response) {
        if (response.status === 200) {
            $('#nickName').val(response.data.nickName);
            $('#name').val(response.data.name);
            $('#gender').val(response.data.gender);
        } else {
            Swal.fire({
                title: 'Error',
                text: response.message,
                icon: 'error'
            });
        }
    }).fail(showError);
}

// 에러처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.message : '알 수 없는 오류';
    Swal.fire({ title: '오류', text: errorMessage, icon: 'error' });
}

function isValidation(data){

    if (data.password !== data.confirmPassword) {
        Toast.fire({icon : 'warning', title : '비밀번호가 일치 하지 않습니다.'});
        return;
    }
    
    if(plan.util.isEmpty(data.nickName)){
        Toast.fire({icon : 'warning', title : '제목을 확인해주세요.'});
        return false;
    } else if(plan.util.isEmpty(data.name)){
        Toast.fire({icon : 'warning', title : '이름을 확인해주세요.'});
        return false;
    } else if(plan.util.isEmpty(data.gender)){
        Toast.fire({icon : 'warning', title : '성별을 확인해주세요.'});
        return false;
    } else if(plan.util.isEmpty(data.password)){
        Toast.fire({icon : 'warning', title : '비밀번호를 확인해주세요.'});
        return false;
    }
    
    return true;
}