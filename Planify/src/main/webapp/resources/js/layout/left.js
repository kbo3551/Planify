/**
 * left.js
 */
$(document).ready(function() {
    leftInit();
});


function leftInit(){
    
    plan.util.getAuthenticatedUserInfo().done(function(userInfo) {
        let html = '';
        let isAdmin = userInfo.roles.some(function(role) {
            return role.roleName === "ROLE_ADMIN";
        });
        let targetArea = $('#roleArea');
        if (isAdmin) {
            targetArea.data('url','/admin/member');
            html = `
                     <li class="nav-item" data-url="/admin/member">
                        <a class="nav-link" href="/admin/member">
                            <i class="fas fa-fw fa-cog"></i>
                            <span>사용자관리</span></a>
                    </li>
                   `;
            $(targetArea).append(html);
        }
        checkMenu();
    }).fail(function(error) {
        console.error(error);
    });
    
}

function checkMenu(){
    var currentUrl = window.location.pathname;

    // data-url을 확인하고, 현재 URL과 비교
    $('#accordionSidebar .nav-item').each(function() {
        var itemUrl = $(this).data('url');
        
        // URL이 포함되면 active 클래스 추가
        if (currentUrl.includes(itemUrl)) {
            $(this).addClass('active');
        }
    });
}