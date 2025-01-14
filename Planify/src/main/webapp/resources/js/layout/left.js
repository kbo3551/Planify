/**
 * left.js
 */
$(document).ready(function() {
    var currentUrl = window.location.pathname;

    // data-url을 확인하고, 현재 URL과 비교
    $('#accordionSidebar .nav-item').each(function() {
        var itemUrl = $(this).data('url');
        
        // URL이 포함되면 active 클래스 추가
        if (currentUrl.includes(itemUrl)) {
            $(this).addClass('active');
        }
    });
});