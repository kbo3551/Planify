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
        $('#nameTag').text(userInfo.name);
    }).fail(function(error) {
        console.error(error);
        $('#nameTag').text('UNKNOWN');
    });
}