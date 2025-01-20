const Toast = plan.util.getToast();

$(document).ready(function () {
    initUserManagement();
    userEventListener();
});

// 초기 설정
function initUserManagement() {
    userTableInit();
}

// 이벤트
function userEventListener() {
    // 저장 버튼 클릭시 발생
    $('#saveUserBtn').on('click', function () {
        const userId = $(this).data('id');
        const user = plan.util.fetchFormData('userForm');
        user.roles = collectRoles(); // roles 데이터 추가

        // 비밀번호가 빈 경우 null 처리
        if (!user.password || user.password.trim() === '') {
            delete user.password;
        }

        const validation = validateUser(user);
        if (validation) {
            if (userId) {
                updateUser(userId, user);
            } else {
                createUser(user);
            }
        }
    });

    // 등록 버튼 클릭
    $('#createUserBtn').on('click', function () {
        plan.util.resetForm('userForm');
        $('#roleUser').prop('checked', false);
        $('#roleAdmin').prop('checked', false);
        $('#memberId').prop('disabled', false);
        $('#saveUserBtn').removeData('id');
    });
}

// 사용자 테이블 초기화
function userTableInit() {
    $('#userTable').DataTable({
        ajax: function (data, callback, settings) {
            plan.util.AJAX_Json('/admin/api/members', '', 'GET', 'json').done(function (response) {
                if (response.status === 200) {
                    const usersWithIndex = response.data.map((item, index) => {
                        item.index = index + 1;
                        return item;
                    });
                    callback({ data: usersWithIndex });
                } else {
                    Swal.fire({
                        title: "Error",
                        text: response.message,
                        icon: "error",
                    });
                    callback({ data: [] });
                }
            }).fail(showError);
        },
        columns: [
            { data: 'index', width: '10%' },
            { data: 'memberId', width: '20%' },
            { data: 'nickName', width: '15%' },
            { data: 'name', width: '15%' },
            { data: 'gender', render: formatGender, width: '10%' },
            { data: 'roles', render: formatRoles, width: '20%' },
            {
                data: null,
                width: '20%',
                render: (data, type, row) =>
                    `<div class="d-flex justify-content-center">
                         <button class="btn btn-sm btn-warning mr-1" onclick="editUser(${row.memberNo})">수정</button>
                         <button class="btn btn-sm btn-danger" onclick="deleteUser(${row.memberNo})">삭제</button>
                     </div>`,
            },
        ],
        order: [[1, 'asc']],
        language: {
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            zeroRecords: "목록이 없습니다.",
            info: "총 _TOTAL_명의 사용자 중 _START_에서 _END_까지 표시",
            infoEmpty: "",
            search: "검색:",
            paginate: {
                first: "처음",
                last: "마지막",
                next: "다음",
                previous: "이전",
            },
        },
        destroy: true,
    });
}

// 사용자 생성
function createUser(user) {
    plan.util.AJAX_Json('/admin/api/members', user, 'POST', 'json').done((response) => {
        Toast.fire({
            icon: 'success',
            title: response.message,
        }).then(() => {
            $('#createUserModal').modal('hide');
            reloadUserList();
        });
    }).fail(showError);
}

// 사용자 수정
function updateUser(userId, user) {
    plan.util.AJAX_Json(`/admin/api/members/${userId}`, user, 'PUT', 'json').done((response) => {
        Toast.fire({
            icon: 'success',
            title: response.message,
        }).then(() => {
            $('#createUserModal').modal('hide');
            reloadUserList();
        });
    }).fail(showError);
}

// 사용자 삭제
function deleteUser(userId) {
    Swal.fire({
        title: "삭제",
        text: "사용자를 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: '취소',
    }).then((result) => {
        if (result.isConfirmed) {
            plan.util.AJAX_Json(`/admin/api/members/${userId}`, '', 'DELETE', 'json').done((response) => {
                Toast.fire({
                    icon: 'success',
                    title: response.message,
                }).then(() => {
                    reloadUserList();
                });
            }).fail(showError);
        }
    });
}

// 사용자 상세 정보 로드
function editUser(userId) {
    plan.util.AJAX_Json(`/admin/api/members/${userId}`, '', 'GET', 'json').done((response) => {
        $('#memberId').val(response.data.memberId);
        $('#nickName').val(response.data.nickName);
        $('#name').val(response.data.name);
        $('#password').val('');
        $('#gender').val(response.data.gender);

        // 권한 체크박스 
        const roles = response.data.roles || [];
        $('#roleUser').prop('checked', roles.includes('ROLE_USER'));
        $('#roleAdmin').prop('checked', roles.includes('ROLE_ADMIN'));

        $('#saveUserBtn').data('id', userId);
        $('#createUserModal').modal('show');
    }).fail(showError);
}

// DataTable 리로드
function reloadUserList() {
    $('#userTable').DataTable().ajax.reload(null, false);
}

// 에러 처리
function showError(jqXHR) {
    const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.message : '알 수 없는 오류';
    Swal.fire({ title: "확인하세요", text: errorMessage, icon: "info" });
}

// 역할 ','형태
function formatRoles(roles) {
    return roles && roles.length > 0 ? roles.join(', ') : '-';
}

// dataTable 성별 표시
function formatGender(gender) {
    switch (gender) {
        case 'MALE':
            return '남성';
        case 'FEMALE':
            return '여성';
        default:
            return '-';
    }
}

// 권한 데이터 배열
function collectRoles() {
    const roles = [];
    if ($('#roleUser').is(':checked')) roles.push('ROLE_USER');
    if ($('#roleAdmin').is(':checked')) roles.push('ROLE_ADMIN');
    return roles;
}

// 유효성 체크
function validateUser(data) {
    if (plan.util.isEmpty(data.memberId) && !$('#memberId').prop('disabled')) {
        Toast.fire({ icon: 'info', title: '아이디를 입력해주세요.' });
        return false;
    } else if (plan.util.isEmpty(data.nickName)) {
        Toast.fire({ icon: 'info', title: '닉네임을 입력해주세요.' });
        return false;
    } else if (plan.util.isEmpty(data.name)) {
        Toast.fire({ icon: 'info', title: '이름을 입력해주세요.' });
        return false;
    }
    return true;
}