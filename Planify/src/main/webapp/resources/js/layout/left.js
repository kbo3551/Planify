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
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>관리</span>
                    </a>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <h6 class="collapse-header">관리</h6>
                            <a class="collapse-item" href="/admin/member">사용자관리</a>
                        </div>
                    </div>
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