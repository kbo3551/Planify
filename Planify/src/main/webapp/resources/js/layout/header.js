/**
 * header.js
 * 
 */
$(document).ready(function() {
    displayName();
});

// 헤더 영역 이름표시
function displayName(){
    plan.util.getAuthenticatedUserInfo().done(function(userInfo) {
        let isAdmin = userInfo.roles.some(function(role) {
            return role.roleName === "ROLE_ADMIN";
        });
        if (isAdmin) {
            $('#h2').show();
        }
        $('#nameTag').text(userInfo.nickName);
    }).fail(function(error) {
        console.error(error);
        $('#nameTag').text('UNKNOWN');
    });
}